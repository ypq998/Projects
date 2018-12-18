$(function() {
	document.getElementById("createTime").value = getSmpFormatDateByLong(
			new Date(), true);
	$('#qty').change(function() {
		var qty = document.getElementById("qty").value;
		var price = document.getElementById("price").value;
		document.getElementById("charge").value = qty * price;
	});
	// $('#saveForm').validate({
	// rules : {
	// name : {
	// required : true
	// },
	// createDate : {
	// required : true
	// }
	// },
	// messages : {
	// name : {
	// required : "必填"
	// },
	// createDate : {
	// required : "必填"
	// }
	// }
	// });
	$('.saveBtn')
			.click(
					function() {
						// alert( $("#saveForm").serialize());
						if ($('#saveForm').valid()) {
							console.log($("#saveForm").serialize());
							$
									.ajax({
										type : "POST",
										url : "/actor/save",
										data : $("#saveForm").serialize(),
										headers : {
											"Content-type" : "application/x-www-form-urlencoded;charset=UTF-8"
										},
										success : function(data) {
											if (data == 1) {
												alert("保存成功");
												pageaction();
												//closeDialog();
											} else {
												alert(data);
											}
										},
										error : function(data) {
											var e;
											$.each(data, function(v) {
												e += v + " ";
											});
											alert(e);
											// alert("error");
										}
									});
						} else {
							alert('数据验证失败，请检查！');
						}
					});

	$('#itemid')
			.change(
					function() {
						//alert($("#saveForm").serialize());
						console.log($("#saveForm").serialize());
						var obj = document.getElementById("itemid"); // 定位id
						var index = obj.selectedIndex; // 选中索引
						var text = obj.options[index].text; // 选中文本
						var value = obj.options[index].value; // 选中值
						document.getElementById("itemname").value = text;
						$
								.ajax({
									type : "POST",
									url : "/actor/getByItemID",
									data : {
										itemid : 2
									},
									headers : {
										"Content-type" : "application/x-www-form-urlencoded;charset=UTF-8"
									},
									success : function(data) {
										document.getElementById("price").value = data.price;
										document.getElementById("remark").value = data.remark;
										document.getElementById("qty").value = data.qty;
										document.getElementById("charge").value = data.charge;
									}
								});
					});

	$('#itemname').blur(function() {
		$('#itemid').children("option").each(function() {
			var found = true;
			if ($(this).text() == $('#itemname').val()) {// 每一个option
				$(this).attr('selected', 'selected');
				$("#itemid").trigger("change");
				found = true;
			}
		});
		if (!found) {
			alert("输入的项不存在");
		}
	});

});
