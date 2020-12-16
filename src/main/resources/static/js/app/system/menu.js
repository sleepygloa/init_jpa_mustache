var menuJs = function(){
	
	var $grid = $('#menuGrid');
	
	return {
		init : function(){
			
			fnList();
			
			getEvents();
		}
	}
	
	function fnList(){
		$grid.fnList({
			programId 	: 'menu',
			programNm 	: '메뉴', 
			editable 	: true,
			url 		: '/common/menu/list',
			colName		: ['MENU_SEQ', 'MENU_PARENT_SEQ', 'PRO_CD', 'MENU_NM', 'MENU_URL', 'MENU_ICO', 'MENU_ORDER', 'USE_YN'],
			colOption	: [
				{id:'MENU_SEQ', 		title:'번호', 			width:"50px"},
				{id:'MENU_PARENT_SEQ', 	title:'부모번호',		width:"50px"},
				{id:'PRO_CD', 			title:'프로그램코드',	    width:"100px"},
				{id:'MENU_NM', 			title:'메뉴명', 		    width:"150px"},
				{id:'MENU_URL',			title:'URL', 			width:"150px"},
				{id:'MENU_ICO', 		title:'아이콘경로',		width:"250px"},
				{id:'MENU_ORDER', 		title:'순번',			width:"50px"},
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