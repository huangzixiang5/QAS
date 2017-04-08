/**
 * 
 */
function checkform(){ 

	var in1 = document.getElementById('in1').value;
	var in2 = document.getElementById('in2').value;
    if(in1.length==0){
    	document.getElementById('in1').value = "";
        document.getElementById('in1').placeholder="*请输入用户名";
        return false;
    }
    if(in1.indexOf(" ") >= 0){
    	document.getElementById('in1').value = "";
        document.getElementById('in1').placeholder="*用户名不能包含空格";
        return false;
    }
    if(in2.length == 0 ){
    	document.getElementById('in2').value = "";
    	document.getElementById('in2').placeholder="*请输入密码";
        return false;
    }
    if(in2.indexOf(" ") >= 0){
    	document.getElementById('in2').value = "";
        document.getElementById('in2').placeholder="*密码不能包含空格";
        return false;
    }
}