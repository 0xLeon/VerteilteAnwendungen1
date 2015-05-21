$(document).ready(function() {
	$('.event > .seat').on('click', function() {
		handleSeat($(this));
	});
});

function handleSeat(seatNode) {
	if (seatNode.hasClass('seat-free') || seatNode.hasClass('seat-own')) {
		var isMarked = seatNode.hasClass('seat-marked');
		var seatID = parseInt(seatNode.data('id'), 10);

		if (isMarked) {
			$('#ticketForm #seat-form-input-' + seatID.toString(10)).remove();
		}
		else {
			var inputElement = $('<input type="hidden" name="seatIDs[]" />');
			inputElement.attr({
				id: 'seat-form-input-' + seatID.toString(10),
				value: seatID.toString(10)
			});
			$('#ticketForm').append(inputElement);
		}

		seatNode.toggleClass('seat-marked');
	}
}
