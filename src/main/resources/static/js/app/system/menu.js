var menuJs = function(){

	var $grid = $('#menuGrid');

	return {
		init : function(){

			fnList();

//			getEvents();
		}
	}

	function fnList(){
		$grid.fnList({
			programId 	: 'menu',
			programNm 	: '메뉴',
			editable 	: true,
			url 		: '/menu/list',
//			colName		: ['MENU_SEQ', 'MENU_PARENT_SEQ', 'PRO_CD', 'MENU_NM', 'MENU_URL', 'MENU_ICO', 'MENU_ORDER', 'USE_YN'],
			colOption	: [
				{id:'menuSeq', 		title:'번호', 			width:"50px"},
				{id:'menuParentSeq', 	title:'부모번호',		width:"50px"},
				{id:'menuLev', 			title:'메뉴레벨',	    width:"100px"},
				{id:'menuCd', 			title:'메뉴코드',	    width:"100px"},
				{id:'menuNm', 			title:'메뉴명', 		    width:"150px"},
				{id:'menuIcon', 		title:'아이콘경로',		width:"250px"},

				{id:'menuOrder', 		title:'순번',			width:"50px"},
				{id:'deviceFlag',		title:'장비플래그',		width:"150px"},
				{id:'USE_YN', 			title:'사용여부', 		width:"50px"},
			],
			btn			: ['addRow', 'delRow', 'saveRow']
		});
	}

	function getEvents(){
		$('#menuTitle').click(function(){
			console.log($grid.getGridData());
		});
	};

}();


$(document).ready(function(){
	menuJs.init();
})