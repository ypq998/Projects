$(function(){
//	$('#saveForm').validate({
//		rules: {
//			name       :{required:true}
//		},submitHandler:function(){
//            return false;
//        },errorPlacement:function(error,ele){
//        	 ele.attr('data',error.html());
//        },success:function(label){
//        	
//        },ignore: ''
// 	});
	$('.saveBtn').click(function(){
//	   if($('#saveForm').valid()){
		  // alert($("#saveForm").serialize());
           $.ajax({
               type: "POST",
               url: "/actor/update",
               data: $("#saveForm").serialize(),
               headers: {"Content-type": "application/x-www-form-urlencoded;charset=UTF-8"},
               success: function (data) {
                   if (data == 1) {
                       alert("编辑成功");
                       pageaction();
                       closeDialog();
                   } else {
                       alert(data);
                   }
               }
           });
//	   }else{
//		   alert('数据验证失败，请检查！');
//	   }
	});

});	
