
var trCnt = -1;

var colRow = null;
var colName = null;
var initData = '';
var viewContents = false;
var viewContentsRe = false;
var blogData = '';
var s_userId = null;

function dataURItoBlob(dataURI) {
    // convert base64/URLEncoded data component to raw binary data held in a string
    var byteString;
    if (dataURI.split(',')[0].indexOf('base64') >= 0)
        byteString = atob(dataURI.split(',')[1]);
    else
        byteString = unescape(dataURI.split(',')[1]);

    // separate out the mime component
    var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];

    // write the bytes of the string to a typed array
    var ia = new Uint8Array(byteString.length);
    for (var i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
    }

    return new Blob([ia], {type:mimeString});
}

//콤보박스 만들기
function fnMakeCombo(targetStr, data){
    $.ajax({
        url      : "/ctrl/settings/code/listCodeCombo",
        data     : {
        	codeGroupCd:data
    	},
        type     : "POST",
        dataType : "json",
        async	 : false,
        success  : function(result) {
        	
        	var targetEl = $('#'+targetStr);
        	targetEl.empty();
        	var str = '';
    		for ( var i = 0; i < result.length ; i++){
    			str += '<option value="'+result[i].NAME+'">'+result[i].VALUE+'</option>'
    		}
    		targetEl.append(str);
        }
    });
}
var clickCnt = -1; //클릭시 행번호
var rowId = -1;
var contentLength = 0;
var focusIdx = -1;
function fnSaveIdx(i){
	focusIdx = i;
}
function fnSaveReIdx(el){
	console.log(el)
}




(function(window, $, undefined){


	//글 컨텐츠 불러오기.
    $.fn.getLoadingPage = function(data){
    	blogData = data;
    	
	  	$.ajax({
//	  		url		: data.url + '/view'+data.uProgramId,
	  		url		: data.url,
	  		type	: "POST",
	  		async	: false,
	  		success	: function(result){
	  			$('#'+tableInitData.programId+'View').empty();
	  			$('#'+tableInitData.programId+'View').html(result);
	  		}
	  	});
    }

    //현재 선택된 행 데이터 리턴
    $.fn.getGridData = function(){
    	return programInitData;
    }

    var tableInitData = {
    		initData 	: null, //초기데이터
    		url			: "", //그리드 URL
    		programId	: "", //화면 프로그램 ID
    		uProgramId	: "", //화면 프로그램 ID 대문자 치환
    		title		: "", //페이지 상단에 보여줄 제목
    		tableTitle	: "", //테이블 상단에 보여줄 테이블 제목
    		grid		: "",
    		
    		//colName		: [],
    		colRow		: [],
    		colOption	: [],
    		viewContents	: false,
    		viewContentsRe	: false,
    		
    		btn			: []
    }
    
    var programInitData = {
    		idx	: "",
    		userId : "",
    }
    
    //그리드 초기화
    $.fn.fnList = function(data){
    	
    	//1. 데이터 세팅
		var gridDataKey = Object.keys(tableInitData);
		var dataKey = Object.keys(data);
		//정의한 변수를 이용한 옵션 변수 세팅
		for ( var i in gridDataKey) {
			var key = gridDataKey[i];
			
			for(var j in dataKey){
				if(key == dataKey[j]){
					//변수마다의 특별한 조건 추가
					//uProgramId 첫 대글자 처리
					if(key == "programId"){
						tableInitData["uProgramId"] = data["programId"].charAt(0).toUpperCase() + data["programId"].slice(1);
					}
					tableInitData[key] = data[dataKey[j]];
				}
			}
		}

    	initData = data;


    	var title = data.programNm + '관리'; //페이지 상단의 페이지 제목
    	var tableTitle = data.programNm + '목록'; //그리드 이름
    	var grid = '';
    	(data.viewContents != undefined? viewContents = data.viewContents : viewContents = false);
    	(data.viewContentsRe != undefined? viewContentsRe = data.viewContentsRe : viewContentsRe = false);

    	colName = new Array();
    	colRow = new Array();
    	colOption = data.colOption;
    	
    	
    	//데이터 세팅
    	var btn = data.btn;

    	//dataSetting
//    	(data.checkBox == true ? checkBox = data.checkBox : checkBox);

    	var editable = false;
    	if(data.editable) editable = data.editable;
    	var editableFlag = false;

    	
    	//그리드 컬럼 만드는 로직
    	//gridData.colName
    	//gridData.colRow
    	$.ajax({
    		//url : tableInitData.url + "list" + tableInitData.uProgramId,
    		url : tableInitData.url,
    		async : false,
    		success : function(result){

    			//키를 가져와 컬럼 이름을 구성
//    			var colList = result.dt_grid[0];
    			if(result.length > 0){
        			var k = Object.keys(result[0]);
        			if(k.length > 0){

        				var tColName = [];
        				//그리드 옵션 중 컬럼 명이 없을
        				//조회된 모든 컬럼 불러옴.

                        for(var j = 0; j < tableInitData.colOption.length; j++){
                            for(var i = 0; i < k.length; i++){
                                if(tableInitData.colOption[j].id == k[i]){
                                    tColName.push(k[i]);
                                    break;
                                }
                            }
                        }
                        tableInitData.colName = tColName;

        			}
    			}
    			tableInitData.colRow = result;
    		}
    	});



    	//그리드 상단 그룹 버튼
    	var gridDivContainer = $('<div class="col-xs-w100" style="margin-bottom:50px;"/>');
    	
    	//페이지 타이틀 
    	var divPageTitle = $('<div class="col-xs-w100" />');
    	var divPageTitleH = $('<h5 class="card-title mb-4">'+tableInitData.tableTitle+'</h5>');
    	
    	divPageTitle.append(divPageTitleH);
    	gridDivContainer.append(divPageTitle);
    	
    	//그리드 버튼 그룹
    	var divBtnGroup = $('<div class="col-xs-w100" />');

    	var gridBtnGrpStr = '<div class="col-xs-w100" >';
	    	if(tableInitData.btn != undefined){
	    		for(var i = tableInitData.btn.length - 1; i >= 0; i--){
	        		var btnName = tableInitData.btn[i];
	        		if(btnName == 'search'){
	        			gridBtnGrpStr += '<a href="#" id="'+tableInitData.programId+'SearchBtn" class="btn btn-save btn-sm pull-right" >검색</a>';
	        		}else if(btnName == 'add'){
	        			gridBtnGrpStr += '<a href="#" id="'+tableInitData.programId+'AddBtn" class="btn btn-save btn-sm pull-right" >글 추가</a>';
	        		}else if(btnName == 'update'){
	        			gridBtnGrpStr += '<a href="#" id="'+tableInitData.programId+'UpdateBtn" class="btn btn-update btn-sm pull-right" >글 수정</a>';
	        		}else if(btnName == 'delete'){
	        			gridBtnGrpStr += '<a href="#" id="'+tableInitData.programId+'DeleteBtn" class="btn btn-delete btn-sm pull-right" >글 삭제</a>';
	        		}else if(btnName == 'addRow'){
	        			gridBtnGrpStr += '<a href="#" id="'+tableInitData.programId+'AddRowBtn" class="btn btn-add btn-sm pull-right" >행추가</a>';
	        		}else if(btnName == 'delRow'){
	        			gridBtnGrpStr += '<a href="#" id="'+tableInitData.programId+'DelRowBtn" class="btn btn-delete btn-sm pull-right" >행삭제</a>';
//	        		}else if(btnName == 'save'){
//	        			gridBtnGrpStr += '<a href="#" id="'+tableInitData.programId+'SaveBtn" class="btn btn-save btn-sm pull-right" >글 저장</a>';
	        		}else if(btnName == 'saveRow'){
	        			gridBtnGrpStr += '<a href="#" id="'+tableInitData.programId+'SaveRowBtn" class="btn btn-save btn-sm pull-right" >행저장</a>';
	        		}
	        	}
	    	}
    	gridBtnGrpStr += '</div>';
    	var gridBtnGrp = $(gridBtnGrpStr);
    	divBtnGroup.append(gridBtnGrp);
//    	gridDivContainer.append(divBtnGroup);


    	//테이블
    	var table = $('<table class="table center-aligned-table table-hover tableScrollX" />');

    	//테이블 헤더
    	var thead = $('<thead />');
    	var thtr = $('<tr />');
//    		thtr.addClass('text-primary');

    	var ththData = '';
    	//FLAG
		ththData += '<th width="50px">'+'flag'+'</th>';
		//FLAG End
    	for(var i = 0; i < tableInitData.colName.length; i++){
			var tdTitle = '', tdWidth = '100px', tdHidden = false, tdHiddenCss = 'show';

			if(tableInitData.colOption != undefined){
				if(tableInitData.colOption[i].id == tableInitData.colName[i]){
					(tableInitData.colOption[i].title != '' ? tdTitle = tableInitData.colOption[i].title : tdTitle = tableInitData.colOption[i].id);
					(tableInitData.colOption[i].width != '' ? tdWidth = tableInitData.colOption[i].width : tdWidth = '');
					(tableInitData.colOption[i].hidden != '' ? tdHidden = tableInitData.colOption[i].hidden : tdHidden = false);
					if(tdHidden) tdHiddenCss = 'none';
				}
			}
    		ththData += '<th style="width:'+tdWidth+'; display:'+tdHiddenCss+'">'+tdTitle+'</th>';
    	}
    	var thth = $(ththData);
    	thead.append(thth);

		/**
		 * Grid Option
		 * Row 에 대한 IDX 값 세팅
		 * tr 시작
		 *
		 * ViewContents == true; --> 글보기
		 * ViewContents == false; --> 글보기 없음
		 * */
    	var tbody = $('<tbody />');

    	for(var i = 0; i < tableInitData.colRow.length; i++){
    		trCnt++;
	    	var tbtr = $('<tr id="tr_row_'+i+'" class="tr_row_'+i+'" />');

	    	//글 상세 보기 속성 있을때 클래스 추가
	    	if(viewContents){
	    		tbtr.addClass('viewContents_'+tableInitData.colRow[i].IDX);
	    	}

			var tbthData = '';
				tbthData += '<td class="td_row_flag" ><i class="fa" ></i><input class="td_row_flag_input" type="hidden" value="" /></th>';


			var rowData = tableInitData.colRow[i];
			var keyName = Object.keys(rowData);

			for(var j = 0; j < tableInitData.colOption.length; j++){
				$.each(rowData, function(k, v){
					var tdTitle = '';
					var tdWidth = '100px';
					var tdHidden = false;
					var tdHiddenCss = 'show';


                    if(tableInitData.colOption[j].id == k){
                        (tableInitData.colOption[j].title != '' ? tdTitle = tableInitData.colOption[j].title : tdTitle = tableInitData.colOption[j].id);
                        (tableInitData.colOption[j].width != '' ? tdWidth = tableInitData.colOption[j].width : tdWidth = '');
                        (tableInitData.colOption[j].hidden != '' ? tdHidden = tableInitData.colOption[j].hidden : tdHidden = false);
                        if(tdHidden) tdHiddenCss = 'none';

						tbthData += '<td class="td_row_'+k+'" style="width:'+tdWidth+';display:'+tdHiddenCss+'">'
						+ '<span class="td_row_s_'+i+'" style="display:show">'+v+'</span>'
						+ '<input type="text" class="td_row_i_'+i+'" value="'+v+'" style="display:none;width:100%" />'
						+ '</td>';
						return false;
					}
				});
			}


			var tbth = $(tbthData);
			tbtr.append(tbth);
			tbody.append(tbtr);
    	}
		table.append(thead);
		table.append(tbody);
		//table End
		
		//버튼 그룹과 테이블 그룹을 한 div로 합침.
		var tableParentDiv = $('<div class="col-xs-w100" />');
		tableParentDiv.append(table);
		divBtnGroup.append(tableParentDiv);
		gridDivContainer.append(divBtnGroup);
		
		//타이틀, 테이블 버튼, 테이블 화면에 그림.
//    	gridDivContainer.append(tableParentDiv);
    	$('#'+data.programId+'Grid').html(gridDivContainer);


    	//
    	if(editable){
    		$('tr[id^="tr_row_"]').dblclick(function(){
    			rowId = $(this).attr('id').split('tr_row_')[1];
    			clickCnt = rowId;

        		if(!editableFlag){
            		$('input[class^="td_row_i_'+rowId+'"').css('display', 'block');
            		$('span[class^="td_row_s_'+rowId+'"').css('display', 'none');
            		editableFlag = true;
        		}else{
            		$('input[class^="td_row_i_'+rowId+'"').css('display', 'none');
            		$('span[class^="td_row_s_'+rowId+'"').css('display', 'block');

            		rowId = -1;
            		editableFlag = false;
        		}
    		});

    	}else{

//    		$('tr[id^="tr_row_"]').click(function(){
//    			gridRowGridBlurEvent($(this));
//    		});
    	}
    	
    	//첫행 블러오기
    	//gridRowGridBlurEvent($('#tr_row_0'));
    }

    /**
     * 블로그 글의 댓글 리스트를 불러오는 소스
     * */
    function getViewReContent(flag, ref, reStep, reLevel){
    	
    	$('#'+tableInitData.programId+'Re').remove();
    	var reBody = $('<div id="'+tableInitData.programId+'Re" class="col-xs-w100" />');

    		SeonhoblogUtil.ajax(
					JSON.stringify(programInitData), 
					tableInitData.url + "listRe" + tableInitData.uProgramId, 
					false, 
					function callbackFunc(result){
						
						//CSS
						var reContent = 0;
						var contentWidth = 90;
						var imgWidth = 10
						
						var dt_grid = result.dt_grid;
//						if(dt_grid.length > 0){
							
							for(var i = -1; i < dt_grid.length; i++){
								
								var dtGridRef = (i == -1? "0" : dt_grid[i].REF);
								var dtGridReStep = (i == -1? "-1" : dt_grid[i].RE_STEP);
								var dtGridReLevel = (i == -1? "1" : dt_grid[i].RE_LEVEL);
								
								if(flag == "READD" || dtGridReLevel == 2){
									reContent = 5;
									contentWidth = 85;
									imgWidth = 10;
								}
								
								var dd = $('<div class="col-xs-w100" />');
								dd.css({
//									"height":"70px"
								});
								
								if(i == -1){
									dd.css({
										"border-bottom" :	"0.5px solid gray",
										"margin": "5px 0px"
									})
								}
								
									var ddTextRe = $('<div class="col-xs-w'+reContent+'" />');
									ddTextRe.css({
										"height"		: "50px",
										"text-align"	: "center"
									});
									ddTextRe.text('ㄴ');
								
									var ddImgDiv = $('<div class="col-xs-w'+imgWidth+'" />');
									ddImgDiv.css({
										"height"		: "50px",
										"text-align"	: "center"
									});
										//이미지//기본이미지.
										var ddImg = $('<img src="" />');
										//사용자 이미지 입력
//										ddImg.src = dt_grid[i].IMG;
										var ddIdFa = '<i class="fa fa-child fa-5x"></i>';
										
										ddImgDiv.append(ddIdFa);
									
									
									var ddDiv = $('<div class="col-xs-w'+contentWidth+'" />');
										var ddIdDiv = $('<div class="col-xs-w100" />');
										ddIdDiv.css({
											"min-height":"25px"
										});
										//신규글쓰기
										if(i == -1){
											
											//로그인 되어있다면
											if(programInitData.userId != ""){
												ddIdDiv.text(programInitData.userId);
											//비로그인
											}else{
												var ddIdText = $('<input id="'+tableInitData.programId+'Writer_'+dtGridRef+'_'+dtGridReStep+'_'+dtGridReLevel+'" class="col-xs-w20" />');
												
												ddIdText.css({
													"border-left":"0px",
													"border-right":"0px",
													"border-top":"0px",
													
												});
												
												ddIdDiv.append(ddIdText);
											}
											
										//글 리스트
										}else{
											ddIdDiv.text(dt_grid[i].IN_USER_ID + "      " + dt_grid[i].IN_DT);
										}
										
										var ddContentDiv = $('<div class="col-xs-w100" />');
										ddContentDiv.css({
											"min-height":"35px"
										});
										
										if(i == -1){
											var ddIdTextArea = $('<textarea id="'+tableInitData.programId+'Content_'+dtGridRef+'_'+dtGridReStep+'" class="col-xs-w100"  />');
											ddIdTextArea.keydown(function(el){
							        			resize(el);
							        		});
											
											ddIdTextArea.css({
												"border-left":"0px",
												"border-right":"0px",
												"border-top":"0px",
												"border-bottom":"0px",
												
											});
											
											ddContentDiv.append(ddIdTextArea);
										}else{
											if(flag == 'VIEW' || flag == "READD"){
												var ddIdTextArea = $('<div id="'+tableInitData.programId+'Content_'+dtGridRef+'_'+dtGridReStep+'_'+dtGridReLevel+'" class="col-xs-w100" />');
												ddIdTextArea.text(dt_grid[i].CONTENT);
												ddContentDiv.append(ddIdTextArea);
											}else{
												console.log(ref, dtGridRef, reStep, dtGridReStep, flag)
												if(ref == dtGridRef && reStep == dtGridReStep && (flag == "RESAVE" || flag == "UPDATE")){
													var ddIdTextArea = $('<textarea id="'+tableInitData.programId+'Content_'+dtGridRef+'_'+dtGridReStep+'_'+reLevel+'" class="col-xs-w100" />');
													ddIdTextArea.text(dt_grid[i].CONTENT);
													ddIdTextArea.keydown(function(el){
									        			resize(el);
									        		});
													
													ddContentDiv.append(ddIdTextArea);
    											}else{
    												var ddIdTextArea = $('<div id="'+tableInitData.programId+'Content_'+dtGridRef+'_'+dtGridReStep+'_'+dtGridReLevel+'" class="col-xs-w100" />');
    												ddIdTextArea.text(dt_grid[i].CONTENT);
    												ddContentDiv.append(ddIdTextArea);
    											}
											}
										}
										
										var ddBtnDiv = $('<div class="col-xs-w100" />');
										ddBtnDiv.css({
											"min-height":"30px"
										});
//										ddBtnDiv.click(function(){
//											fnSaveReIdx(ddBtnDiv);
//										})
										
										//댓글달기
										var ddBtnNew = $('<a class="btn" id="'+tableInitData.programId+'ReAddBtn_'+dtGridRef+'_'+dtGridReStep+'_'+dtGridReLevel+'" />');
										ddBtnNew.text('댓글달기');

										ddBtnNew.click(function(){
											
											var el = $(this);
											var split = el.attr("id").split("_");
											var deleteRef = split[1];
											var deleteReStep = split[2];
											var deleteReLevel = Number(split[3])+1;
											
											getViewReContent('READD', deleteRef, deleteReStep, deleteReLevel);
										})
										
										//수정 전환
										var ddBtnUpdate = $('<a class="btn" id="'+tableInitData.programId+'ReContentPlace_'+dtGridRef+'_'+dtGridReStep+'_'+dtGridReLevel+'" />');
										ddBtnUpdate.text('수정');
										
										ddBtnUpdate.click(function(){
											
											var el = $(this);
											var split = el.attr("id").split("_");
											var deleteRef = split[1];
											var deleteReStep = split[2];
											var deleteReLevel = split[3];
											
											getViewReContent('UPDATE', deleteRef, deleteReStep, deleteReLevel);
										})
										
										//신규 저장
										var ddBtnSave = $('<a class="btn" id="'+tableInitData.programId+'ReSaveBtn_'+dtGridRef+'_'+dtGridReStep+'_'+dtGridReLevel+'" />');
										ddBtnSave.text('저장');
										
										ddBtnSave.click(function(){
											
											var el = $(this);
											var split = el.attr("id").split("_");
											var deleteRef = split[1];
											var deleteReStep = split[2];
											var deleteReLevel = split[3];
											
											var content = $('#'+tableInitData.programId+'Content_'+deleteRef+'_'+deleteReStep).val();
											if(content == ''){
												alert('내용을 입력해주세요');
												return false;
											}
											
								    		SeonhoblogUtil.ajax(
													JSON.stringify({
														idx	: programInitData.idx,
														ref	: deleteRef,
														reStep	: deleteReStep,
														reLevel	: deleteReLevel,
														writer	: $('#'+tableInitData.programId+'Writer_'+deleteRef+'_'+deleteReStep).val(),
														content	: $('#'+tableInitData.programId+'Content_'+deleteRef+'_'+deleteReStep).val(),
														flag	: 'INSERT'
													}), 
													tableInitData.url + "saveRe" + tableInitData.uProgramId, 
													false, 
													function callbackFunc(result){
														getViewReContent('VIEW');
													}
												);
										})
										
										
										//수정 저장
										var ddBtnUpdateSave = $('<a class="btn" id="'+tableInitData.programId+'ReUpdateBtn_'+dtGridRef+'_'+dtGridReStep+'_'+dtGridReLevel+'" />');
										ddBtnUpdateSave.text('저장');
										
										ddBtnUpdateSave.click(function(){
											
											var el = $(this);
											var split = el.attr("id").split("_");
											var deleteRef = split[1];
											var deleteReStep = split[2];
											var deleteReLevel = split[3];
											
											var content = $('#'+tableInitData.programId+'Content_'+deleteRef+'_'+deleteReStep+'_'+deleteReLevel).val();
											if(content == ''){
												alert('내용을 입력해주세요');
												return false;
											}
											
								    		SeonhoblogUtil.ajax(
													JSON.stringify({
														idx	: programInitData.idx,
														ref	: deleteRef,
														reStep	: deleteReStep,
														reLevel	: deleteReLevel,
														writer	: $('#'+tableInitData.programId+'Writer_'+deleteRef+'_'+deleteReStep+'_'+deleteReLevel).val(),
														content	: $('#'+tableInitData.programId+'Content_'+deleteRef+'_'+deleteReStep+'_'+deleteReLevel).val(),
														flag	: 'UPDATE'
													}), 
													tableInitData.url + "saveRe" + tableInitData.uProgramId, 
													false, 
													function callbackFunc(result){
														getViewReContent('VIEW');
													}
												);
										})
										
										//댓댓글 신규 저장
										var ddBtnReSave = $('<a class="btn" id="'+tableInitData.programId+'ReReSaveBtn_'+dtGridRef+'_'+dtGridReStep+'_'+reLevel+'" />');
										ddBtnReSave.text('대댓글 저장');
										
										ddBtnReSave.click(function(){
											
											var el = $(this);
											var split = el.attr("id").split("_");
											var deleteRef = split[1];
											var deleteReStep = split[2];
											var deleteReLevel = split[3];
											
											var content  = $('#'+tableInitData.programId+'Content_'+deleteRef+'_'+deleteReStep+'_'+deleteReLevel).val()
											if(content == ''){
												alert('내용을 입력해주세요');
												return false;
											}
											
								    		SeonhoblogUtil.ajax(
													JSON.stringify({
														idx	: programInitData.idx,
														ref	: deleteRef,
														reStep	: deleteReStep,
														reLevel	: deleteReLevel,
														writer	: $('#'+tableInitData.programId+'Writer_'+deleteRef+'_'+deleteReStep+'_'+deleteReLevel).val(),
														content	: $('#'+tableInitData.programId+'Content_'+deleteRef+'_'+deleteReStep+'_'+deleteReLevel).val(),
														flag	: 'INSERT'
													}), 
													tableInitData.url + "saveRe" + tableInitData.uProgramId, 
													false, 
													function callbackFunc(result){
														getViewReContent('VIEW');
													}
												);
										})
										
										//삭제
										var ddBtnDelete = $('<a class="btn" id="'+tableInitData.programId+'ReDelBtn_'+dtGridRef+'_'+dtGridReStep+'_'+dtGridReLevel+'" />');
										ddBtnDelete.text('삭제');
										
										ddBtnDelete.click(function(){
											
											var el = $(this);
											var split = el.attr("id").split("_");
											var deleteRef = split[1];
											var deleteReStep = split[2];
											var deleteReLevel = split[3];
											
								    		SeonhoblogUtil.ajax(
													JSON.stringify({
														idx	: programInitData.idx,
														ref	: deleteRef,
														reStep	: deleteReStep,
														reLevel	: deleteReLevel,
														flag	: 'DELETE'
													}), 
													tableInitData.url + "saveRe" + tableInitData.uProgramId, 
													false, 
													function callbackFunc(result){
														getViewReContent('VIEW');
													}
												);
										})
										
										
										//버튼 그룹
//										if(dtGridReStep == 0 && flag != 'UPDATE' && flag != 'RESAVE'){
										if(dtGridReStep == 0 && flag == 'VIEW'){
											if(dtGridReLevel == 1){
												ddBtnDiv.append(ddBtnNew);
											}
										}
										
										//신규 댓글 저장
										if(i == -1){
											ddBtnDiv.append(ddBtnSave);	
											
										//댓글 리스트 
										}else{
											
											//수정, 삭제
//											if(reLevel == 1){
												if(flag == 'VIEW'){
													if(programInitData.userId == dt_grid[i].IN_USER_ID){
														ddBtnDiv.append(ddBtnUpdate);
														ddBtnDiv.append(ddBtnDelete);
													}	
												}
//											}else{

//											}
												
											if(ref == dtGridRef && reStep == dtGridReStep){
												//저장 
												if(flag == "UPDATE"){
													ddBtnDiv.append(ddBtnUpdateSave);
												}else if(flag == "RESAVE"){
													ddBtnDiv.append(ddBtnReSave);
												}
											}
										}
										
										
										//댓글 컨텐츠 그룹
										ddDiv.append(ddIdDiv);
										ddDiv.append(ddContentDiv);
										ddDiv.append(ddBtnDiv);
										
								//댓글 이미지와 컨텐츠그룹
								if(i != -1 && ref == dtGridRef && dtGridReLevel == 2){ dd.append(ddTextRe); }
								if(i != -1 && dtGridReLevel == 2){ dd.append(ddTextRe); }
								dd.append(ddImgDiv).append(ddDiv);
								
								//댓글영역 추가
								reBody.append(dd);
								
								if(i != -1 && flag == "READD" && ref == dtGridRef && reLevel == 2){
									--i;
									flag = "RESAVE";
									continue;
								}
								//대댓글 영역 추가 완료 시 플래그 
								if(flag == "RESAVE"){
									reLevel = 1;
								}
							}
//						}
					}
				);
    		
    		$('#'+tableInitData.programId+'ViewArea').after(reBody);
    }
    

    //행 선택 시 데이터 세팅
//    function gridDataSetting(thisData){
//    	var thisData = thisData;
//    	var rowId = thisData.attr('class').split('tr_row_')[1];
//    	var tdData = thisData.children('td');
//    	var tdRowData = {};
//    	clickCnt = rowId;
//
//    	programInitData.idx = rowId;
//    	var faFlag = null;
//
//    	var elI = $(tdData[0]).children('i');
//
//    	if(elI.hasClass('fa-plus')){
//    		for(var i = 0; i < tdData.length; i++){
//        		var el = $(tdData[i]);
//
//    			var inputData = el.children('input')[0].value;
//        		var inputKeyTrans = el.attr('class').split('td_row_')[1].toLowerCase();
//
//        		var inputKeyArr = inputKeyTrans.split("_");
//        		var inputKey = '';
//
//        		for(var j = 0 ; j < inputKeyArr.length;j++){
//        			if(j == 0){
//        				inputKey += inputKeyArr[j];
//        			}else{
//        				inputKey += inputKeyArr[j].charAt(0).toUpperCase() + inputKeyArr[j].slice(1);
//        			}
//        		}
//        		tdRowData[inputKey] = inputData;
//        	}
//    	}else{
//    		for(var i = 0; i < tdData.length; i++){
//        		var el = $(tdData[i]);
//
//        		var tdKeyTrans = el.attr('class').split('td_row_')[1].toLowerCase();
//        		var tdKeyArr = tdKeyTrans.split("_");
//        		var tdKey = '';
//
//        		for(var j = 0 ; j < tdKeyArr.length;j++){
//        			if(j == 0){
//        				tdKey += tdKeyArr[j];
//        			}else{
//        				tdKey += tdKeyArr[j].charAt(0).toUpperCase() + tdKeyArr[j].slice(1);
//        			}
//        		}
//        		if(i == 0){
//        			var tdFlagData = el.children('.td_row_flag_input')[0].value;
//        			tdRowData[tdKey] = tdFlagData;
//        		}else{
//        			tdRowData[tdKey] = el.text();
//        		}
//        	}
//    	}
//    	programInitData = tdRowData;
//    }

//    function gridRowGridBlurEvent(thisData){
//
//    	//클릭했을때 programInitData 에 td 속성 값들 저장
//    	gridDataSetting(thisData);
//
//    	if(programInitData.idx != undefined){
//    		getView('VIEW');
//    	}
//    }

    function getView(flag){
		if(viewContents){
			//내용 초기화
			$('#'+tableInitData.programId+'Title').empty();
			$('#'+tableInitData.programId+'Subject').empty();
			$('#'+tableInitData.programId+'ViewArea').empty();
			
			$('#'+tableInitData.programId+'View').css('display', 'block');
			fnMakeCombo(tableInitData.programId+'Title', 'BLOG_TITLE_CD');


			//컨텐츠 개수 초기화
	    	contentLength = 0;
	    	$.ajax({
				url	 	: tableInitData.url + 'view'+ tableInitData.uProgramId,
				data 	: JSON.stringify(programInitData),
				type 	: "POST",
				async	: false,
				contentType : 'application/json; charset=utf-8',
				success : function(result){
					
					//CSS
					$('#'+tableInitData.programId+'ViewArea').css({
						"padding-bottom"	: "20px",
						"border-bottom" 	: "0.5px solid gray"
					});
					
					//블로그 내용 초기화
					$('#'+tableInitData.programId+'ViewArea').empty();
					
					var dt_grid = result.dt_grid;
						
						//타이틀, 주제 입력
						$('#'+tableInitData.programId+'Title option:contains('+dt_grid[0].TITLE+')').attr('selected', 'selected');
						$('#'+tableInitData.programId+'Subject').val(dt_grid[0].SUBJECT);
						
						//컨텐츠 입력(루프)
						for(var i = 0; i < dt_grid.length; i++){
							if(flag == 'VIEW'){
								if(dt_grid[i].TYPE == 'IMG'){
									fnAddImg(flag, dt_grid[i]);
								}else if(dt_grid[i].TYPE == 'CODE'){
									fnAddTextBox(flag, dt_grid[i], dt_grid[i].TYPE);
								}else{
									fnAddTextBox(flag, dt_grid[i], dt_grid[i].TYPE);
								}
							}else{
								if(dt_grid[i].TYPE == 'IMG'){
									fnAddImg(flag, dt_grid[i]);
								}else if(dt_grid[i].TYPE == 'CODE'){
									fnAddTextBox(flag, dt_grid[i], dt_grid[i].TYPE);
								}else{
									fnAddTextBox(flag, dt_grid[i], dt_grid[i].TYPE);
								}
							}
						}

						//유저 세팅
						programInitData.userId = dt_grid[0].IN_USER_ID;
						
						if(flag != 'VIEW' && seonhoblogData.s_userId == dt_grid[0].IN_USER_ID){
							$('.'+programInitData.programId+'UpdateFlag').css('display', 'block');
						}
						
						
						if(viewContentsRe){
							getViewReContent('VIEW');
						}
				}
			})

		}


		
		
		//글쓴이 일때만 글 수정 
		//글쓴이가 아니면 수정 양 식 숨김

    }

    //그리드와 관련된 버튼을 클릭 했을때 이벤트
//    $(document).on('click','a[id$=Btn]', function(e){
//    	var thisId = $(this).attr('id');
//
//    	var flag = '';
//    	var newUrl = '';
//    	var sendData = '';
//    	var gridData = {};
//
//    	if(thisId.indexOf('TextBoxAdd') != -1){
//    		fnAddTextBox('INSERT',  {}, 'TEXT');
//    		return false;
//    	}else if(thisId.indexOf('CodeAdd') != -1){
//    		fnAddTextBox('INSERT', {}, 'CODE');
//    		return false;
//    	}else if(thisId.indexOf('ImgAdd') != -1){
//    		fnAddImg('INSERT',  {});
//    		return false;
//    	}else if(thisId.indexOf('DelBox') != -1){
//
//			$('#row_'+focusIdx).remove();
//			return false;
//    	//댓댓글 저장
//    	}else if(thisId.indexOf('ReReSaveBtn') != -1){
//
//    		flag = 'insert';
//    		newUrl = 'saveReRe' + tableInitData.uProgramId;
//    		if($('#'+tableInitData.programId+'ReReWriter').val() == ''){
//    			alert('로그인을 해주세요.');
//    			return false;
//    		}else if($('#'+tableInitData.programId+'ReReContent').val() == ''){
//    			alert('내용을 입력해주세요');
//    			return false;
//    		}
//
//    		var saveInput = $(this).children('input');
//
//    		sendData = {
//    				idx		:	blogData.idx,
//    				writer	:	$('#'+tableInitData.programId+'ReReWriter').val(),
//    				content	:	$('#'+tableInitData.programId+'ReReContent').val(),
//    				ref		:   saveInput[0].value,
//    				reStep  :   saveInput[1].value
//    		}
//
//    	//댓글추가
//    	}else if(thisId.indexOf('ReAddBtn') != -1){
//
//    		var addInput = $(this).children('input');
//    		var getRef = addInput[0].value;
//    		var gerReStep = addInput[1].value;
//    		var refId = '#'+tableInitData.programId+'ReContentPlace_'+getRef+'_'+gerReStep;
//
//			var reContentNew =
//				'<div class="col-lg-12 re-contents-input" style="display:inline-block">'
//
//					+ '<div class="" style="float:left;"><a><img class="re-cp-img" src="#"  /></a></div>'
//					+ '<input id="'+tableInitData.programId+'ReReWriter" class="col-lg-2" type="text" value="'+(s_userId == null? '' : s_userId)+'" style="padding:0px;display:none;" />'
//					+ '<div class="" style="float:right;min-width:220px;width:90%" ><textarea id="'+tableInitData.programId+'ReReContent" style="height:100%;width:100%" ></textarea></div>'
//					+ '<div style="float:right;min-width:220px;width:90%;height:50px;">'
//
//
//						+ '<a class="btn btn-default" id="'+tableInitData.programId+'ReReSaveBtn">댓글쓰기'
//						+ '<input class="ref" type="hidden" value="'+getRef+'" />'
//						+ '<input class="reStep" type="hidden" value="'+gerReStep+'" />'
//						+ '</a>'
//					+ '</div>'
//				+ '</div>';
//
//    		$(refId).append(reContentNew);
//    		return false;
//    	//검
//    	}else if(thisId.indexOf('Search') != -1){
//
//    		flag = 'search';
//    		newUrl = 'get' + tableInitData.uProgramId;
//    	//그리드행추가
//    	}else if(thisId.indexOf('AddRow') != -1){
//    		trCnt++;
//    		clickCnt = trCnt;
//
//    		var str = '<tr class="tr_row_'+trCnt+'" >';
//			str += '<td class="td_row_flag">';
//				str += '<i class="fa fa-plus"></i>';
//				str += '<input class="td_row_flag_input input-sm" type="hidden" value="INSERT" />';
//			str += '</td>';
//    		$.each(tableInitData.colName, function(i, v){
//    			str += '<td class="td_row_'+v+'">';
//    			str += '<input class="td_row_'+v+'_input input-sm" type="text" value="" />';
//    			str += '</td>';
//    		});
//    			str += '</tr>';
//			$('tbody').append(str);
//    		return false;
//    	//그리드행삭제
//    	}else if(thisId.indexOf('DelRow') != -1){
//    		var delI = $('.tr_row_'+clickCnt).find('i')[0];
//    		var delInput = $('.tr_row_'+clickCnt).find('input.td_row_flag_input')[0];
//    		if(delInput.value == ""){
//    			delI.classList.add('fa-minus');
//    			delInput.value = "delete";
//    		}else{
//    		}
//    		gridRowGridBlurEvent($('.tr_row_'+clickCnt));
//			return false;
//    	//그리드 행 저장
//    	}else if(thisId.indexOf('SaveRow') != -1){
//    		var saveId = $('.tr_row_'+clickCnt);
//    		var saveTds = saveId.find('td');
//
//    		var saveData = {};
//
//    		saveData["idx"] = clickCnt;
//
//    		for(var j = 0; j < saveTds.length; j++){
//    			var rowNm = $(saveTds[j]).attr('class').split('td_row_')[1];
//
////    			if(rowNm == 'flag') continue;
//
//    			var rowNmSplit = rowNm.split('_');
//    			var str = '';
//    			for(var k = 0; k < rowNmSplit.length; k++){
//    				if(k == 0){
//    					str += rowNmSplit[k].toLowerCase();
//    				}else{
//    					var rowNmSplitChar = rowNmSplit[k].toLowerCase();
//    					str += rowNmSplitChar.charAt(0).toUpperCase() + rowNmSplitChar.slice(1);
//    				}
//    			}
//
//    			var saveSpan = $(saveTds[j]).find('span').text();
//    			var saveInput = $(saveTds[j]).find('input').val();
//
//    			gridDataSetting(saveId);
//    			if(saveSpan == saveInput) {
//    				saveData[str] = saveSpan;
//    			}else{
//        			saveData[str] = saveInput;
//    			};
//    		}
//
//    		if(saveData["flag"] != "INSERT"){
//    			saveData["flag"] = "UPDATE";
//    		}
//
//    		gridData = saveData;
//
//    		newUrl = 'update'+ tableInitData.uProgramId;
//		//글추가
//    	}else if(thisId.indexOf('Add') != -1){
//    		if($('#'+tableInitData.programId+'View').css('display') == "none"){
//        		$('#'+tableInitData.programId+'View').css('display', 'block');
//    		}
//
//			$('.'+tableInitData.programId+'UpdateFlag').css('display', 'block');
//
//			//내용 초기화
//			$('#'+tableInitData.programId+'Title').empty();
//			$('#'+tableInitData.programId+'Subject').empty();
//			$('#'+tableInitData.programId+'ViewArea').empty();
//
//    		fnMakeCombo(tableInitData.programId+'Title', 'BLOG_TITLE_CD');
//        	return false;
//        //글저장
//    	}else if(thisId.indexOf('Save') != -1){
//			newUrl = 'update'+ tableInitData.uProgramId;
//
//
////			var form = $('mainBlogUpdateForm')[0];
////			var formData = new FormData(form);
////
////			if($('#'+tableInitData.programId+'FileUploadText').val() != ''){
////				formData.append('file_0', $('#'+ tableInitData.programId+'FileUpload')[0].files[0]);
////			}
//
//
//
//			var dataDt = [];
//			var count = 0;
//			for(var i = 0; i < contentLength; i++){
//
//				//글(컨텐츠) 가 작성된 개수, 있을 때.
//				if($('#row_'+i).val() != undefined){
//
//					//데이터 세팅
//					var initData = {
//							text		: $('#text_'+i).val(),
//							type			: $('#type_'+i).val(),
//							imgWidthScale	: $('#text_'+i).attr('width')
//					}
//
//
//					//이미지 일 경우
//					if(initData.type == 'IMG'){
//
//						initData.text = $('#text_'+i).attr('src');
////						initData.text = dataURItoBlob($('#text_'+i).attr('src'));
//						if(initData.imgWidthScale != ''){
//							initData.imgWidthScale = initData.imgWidthScale.replace(/[^0-9]/g,"");
//						}
//					}
//
//					//바인딩
//					var	dataList = {
//							idx 			: programInitData.idx,
//							i   			: i-count,
//							type 			: initData.type,
//							content 		: initData.text,
//							imgWidthScale 	: initData.imgWidthScale,
//					}
//					dataDt.push(dataList);
//				}else{
//					count++;
//				}
//			}
//
//			var sendData = {
//				title : $('#'+tableInitData.programId+'Title option:selected').text(),
//				subject : $('#'+tableInitData.programId+'Subject').val(),
//				dt_data  : dataDt
//			}
//
//			if(programInitData.idx != ''){
//				sendData["idx"] = programInitData.idx;
//			}
//
//			SeonhoblogUtil.ajax(
//					JSON.stringify(sendData),
//					tableInitData.url + "save" + tableInitData.uProgramId + "Contents",
//					false,
//					function callbackFunc(data){
//
////						if($('#'+tableInitData.programId+'FileUploadText').val() != ''){
////						$.ajax({
////							url 	: tableInitData.url + "/save"+tableInitData.uProgramId+"FileUpload",
////							type	: 'POST',
////							data    : formData,
////							contentType : false,
////							processData : false,
////							async 	: false,
////							success	: function(){
////								if(result.SUCCESS){
////									alert(result.SUCCESS);
////
////								}
////							}
////						})
////					}
//
//						window.location.href="/";
//					}
//			)
//
//
//return false;
//
//		//글수정
//    	}else if(thisId.indexOf('Update') != -1){
//
//    		$('.'+tableInitData.programId+'UpdateFlag').css('display', 'block');
//
//			getView('UPDATE');
//			return false;
//		//글삭제
//    	}else if(thisId.indexOf('Delete') != -1){
//
//    		flag = 'delete';
//    		newUrl = 'modify' + tableInitData.uProgramId
//    	//글취소
//    	}else if(thisId.indexOf('Cancel') != -1){
//
//    		flag = 'cancel';
//    		window.location.href="/";
//    		return false;
//    	}
//    	if(thisId.indexOf('Box') != -1){
//
//    	}else if(thisId.indexOf('Re') != -1){
//    		$.ajax({
//    			url		: tableInitData.url + newUrl,
//        		type	: "POST",
//        		data	: JSON.stringify(sendData),
//        		dataType : "json",
//                async	: false,
//    			contentType : "application/json, charset=utf-8",
//    			success : function(result){
//    				$('#'+tableInitData.programId+'Re').empty();
//    				$('#'+tableInitData.programId+'Re').getBlogRe();
//    			}
//    		});
//    	}else if(thisId.indexOf('Row') != -1){
//        	$.ajax({
//        		url		: tableInitData.url + newUrl,
//        		type	: "POST",
//        		data	: JSON.stringify(gridData),//???????????????????
//                dataType: 'json',
//                async	: false,
//    			contentType : "application/json, charset=utf-8",
//        		success : function(data){
//
//        			$('#'+tableInitData.programId+'Grid').remove();
//        			$('#'+tableInitData.programId+'Grid').fnList(initData);
//        		}
//        	});
//    	}else{
//        	$.ajax({
//        		url		: tableInitData.url + newUrl,
//        		type	: "POST",
//        		data	: JSON.stringify(blogData),
//                dataType: 'json',
//                async	: false,
//    			contentType : "application/json, charset=utf-8",
//        		success : function(data){
//
//        			$('#view'+tableInitData.uProgramId).empty();
//        			$('#view'+tableInitData.uProgramId).html(data);
//        		}
//        	});
//    	}
//    });



    
    //텍스트상자 높이 조정
    function resize(obj) {
  	  obj.currentTarget.style.height = "50px";
  	  obj.currentTarget.style.height = (12+obj.target.scrollHeight)+"px";
  	}



    function fnAddTextBox(flag, data, type){
    	var cnt = contentLength;
    	var dd = $('<div id="row_'+cnt+'" class="col-xs-w100" />');

    		var childInput1 = $('<input type="hidden" id="idx_'+cnt+'" value="'+cnt+'" />');
    		var childInput2 = $('<input type="hidden" id="type_'+cnt+'" value="TEXT" />');
    		
    		if(type == "TEXT"){
    			childInput2.val('TEXT');	
    		}else{
    			childInput2.val('CODE');
    		}
    		
    		dd.append(childInput1).append(childInput2);
    		
    		//삽입,수정
    		if(flag != "VIEW"){
        		var childTextArea = $('<textarea id="text_'+cnt+'" class="col-xs-w100" style="min-height:50px; padding:10px;" />');
        		childTextArea.keydown(function(el){
        			resize(el);
        		});
        		
        		if(flag == "UPDATE"){
        			childTextArea.text(data.CONTENT);
        		}else{

        		}
        		if(type == "CODE"){
            		childTextArea.css({
	        			"background-color" 	: "gray",
	        			"color"				: "white"
	        		})
        		}
        		if(type == "CODE"){
            		childTextArea.css({
	        			"background-color" 	: "gray",
	        			"color"				: "white"
	        		})
        		}
        		
        		dd.append(childTextArea);
        		
        		dd.click(function(){
        			fnSaveIdx(cnt);
        		})
    		}else{
        		var childTextArea = $('<div id="text_'+cnt+'" class="col-xs-w100" style="min-height:50px; padding:10px;" />');
//        		childTextArea.keydown(function(el){
//        			resize(el);
//        		});
//        		childTextArea.css({
//        			"text-align"		: "center"
//        		})
        		
        		childTextArea.text(data.CONTENT);
        		if(type == "CODE"){
            		childTextArea.css({
	        			"background-color" 	: "gray",
	        			"color"				: "white"
	        		})
        		}
        		
        		dd.append(childTextArea);
    		}
    		
    		$('#'+tableInitData.programId+'ViewArea').append(dd);
    		
	    	contentLength++;
	    	focusIdx = contentLength;
    }
    
    
    function fnAddImg(flag, data){
    	var cnt = contentLength;
    	var dd = $('<div id="row_'+contentLength+'" class="col-xs-w100" />');
    	
    	//쓰기, 수정
    	if(flag != "VIEW"){
    		var childInput0 = $('<input type="file" id="img_'+cnt+'" "display:none" />');
//    		var childInput0 = $('#'+tableInitData.programId+'ImgAddBtn_input');
    		childInput0.change(function(){
    			//이미지 변경
    		    if (this.files && this.files[0]) {
    		        var reader = new FileReader();
    		        
    		        reader.onload = function (e) {
    		        	
    		        	var img = new Image();
    		        	img.src = e.target.result;
    		        	img.onload = function(e){
        		        	var canvas = document.createElement("canvas"),
    		            	max_size = 640,
	    		            width = img.width,
	    		            height = img.height;

		        		if (width > height) {
		        			//가로가 길 경우
		        			if (width > max_size) {
		        				height *= max_size / width;
		        				width = max_size;
		        			}
		        		} else {
		        			//세로가 길 경우
		        			if (height > max_size) {
		        				width *= max_size / height;
		        				height = max_size;
		        			}
		        		}
		        		
		        		canvas.width = width;
		        		canvas.height = height;
		        		canvas.getContext("2d").drawImage(img, 0, 0, width, height);
		        		var dataUrl = canvas.toDataURL("image/jpeg");

    		            $('#text_'+cnt).attr('src', dataUrl);
    		        	$('#text_'+cnt).attr('width', '100%');
    		        	}
    		        	
//
    		        }
    		        reader.readAsDataURL(this.files[0]);
    		    }
    		});
    		if(flag == 'INSERT'){
    			childInput0.trigger('click');
    		}
    	}
		
		var childInput1 = $('<input type="hidden" id="idx_'+cnt+'" value="'+cnt+'" />');
		var childInput2 = $('<input type="hidden" id="type_'+cnt+'" value="IMG" />');
		dd.append(childInput1).append(childInput2);
		
		var childImg = $('<img id="text_'+cnt+'" />');
		if(flag != 'INSERT'){ //글보기와 글수정만
			childImg.attr('src', data.CONTENT);
			childImg.attr('width', data.IMG_WIDTH_SCALE+'%');			
		}

		
		dd.append(childImg);
		
		if(flag != "VIEW"){
			
    		dd.mouseover(function(){
    			$('.imgContWidth_'+cnt).css('display', 'block');
    		}).mouseleave(function(){
    			$('.imgContWidth_'+cnt).css('display', 'none');
    		}).click(function(){
    			fnSaveIdx(cnt);
    		})
			
			var childImgBtn1 = $('<button class="col-xs-w5 imgContWidth_'+cnt+'" style="display:none">10%</button>');
				childImgBtn1.click(function(){
					$('#text_'+cnt).attr('width', '10%');
				});
			var childImgBtn2 = $('<button class="col-xs-w5 imgContWidth_'+cnt+'" style="display:none">25%</button>');
				childImgBtn2.click(function(){
					$('#text_'+cnt).attr('width', '25%');
				});
			var childImgBtn3 = $('<button class="col-xs-w5 imgContWidth_'+cnt+'" style="display:none">50%</button>');
				childImgBtn3.click(function(){
					$('#text_'+cnt).attr('width', '50%');
				});
			var childImgBtn4 = $('<button class="col-xs-w5 imgContWidth_'+cnt+'" style="display:none">75%</button>');
				childImgBtn4.click(function(){
					$('#text_'+cnt).attr('width', '75%');
				});
			var childImgBtn5 = $('<button class="col-xs-w5 imgContWidth_'+cnt+'" style="display:none">100%</button>');
				childImgBtn5.click(function(){
					
					$('#text_'+cnt).attr('width', '100%');
				});
			
			dd	.append(childImgBtn1)
				.append(childImgBtn2)
				.append(childImgBtn3)
				.append(childImgBtn4)
				.append(childImgBtn5);
		}

    	
		
		$('#'+tableInitData.programId+'ViewArea').append(dd);
		
    	contentLength++;
    	focusIdx = contentLength;
    }
    
}(window, jQuery));




var SeonhoblogUtil = function() {
	"use strict";

	return {
		ajax : function(jsonData, saveUrl, msg, callback, sucMsgFlag){


			//ajax 펑션 유효성검사
			if(saveUrl == undefined){
				alert('');
				return false;
			}

			if(msg != false){
		        //cofirm Message
		        if (!confirm((Util.confirm(msg)).msgTxt)) return;
			}

			//데이터 request 잇음. dt_grid
			if(jsonData != undefined){
		        $.ajax({
		            url      : saveUrl,
		            data     : jsonData,
		            dataType : 'json',
		            type     : 'POST',
		            cache    : false,
		            async	 : false,
		            contentType : 'application/json; charset=utf-8',
		            success  : function(data) {
		            	callback(data);
		            }
		        });
		    //데이터 request 없음.
			}else{
		        $.ajax({
		            url      : saveUrl,
//		            data     : jsonData,
		            dataType : 'json',
		            type     : 'POST',
		            cache    : false,
		            async	 : false,
		            contentType : 'application/json; charset=utf-8',
		            success  : function(data) {
		            	callback(data);
		            }
		        });
			}


		},
		
	}
	
}();

