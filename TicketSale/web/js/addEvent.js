// addEvent.js

$(document).ready(function() {
	var datePickerConfig = {
		language: "de",
		todayBtn: true,
		autoclose: true,
		todayHighlight: true
	};
	$('#reservationDeadline').closest('.date').datepicker(datePickerConfig);
	$('#purchaseDeadline').closest('.date').datepicker(datePickerConfig);
});
