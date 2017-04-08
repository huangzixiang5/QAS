/**
 * showList.jsp 中使用的检验复选框函数
 */

function show(i,q,a) { //点击时设置显示问题及答案的具体内容
	self.parent.frames["right"].document.getElementById("i").value =i ;
	self.parent.frames["right"].document.getElementById("q").value =q ;
	self.parent.frames["right"].document.getElementById("a").value =a ;
}

function isChecked(){   //批量删除时判断是否选中，有选中则提交
    var count = 0;  
    var checkArray = document.getElementsByName("id");  
    for ( var i = 0; i < checkArray.length; i++) {  
        if(checkArray[i].checked == true){  
            //在此可以对选中的标签进行操作  
            count ++; 
            document.getElementById("delete").submit() ;
            break;
        }  
    }  
    if(count == 0){  
        //至此，说明没有标签被选择到，可以进行相应的操作  
        alert("至少选择一个！")  
    }
    
}  
