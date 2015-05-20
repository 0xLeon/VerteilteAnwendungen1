$(document).ready(function() {
	$('.seat').on('click', function() {
		handleSeat($(this));
	});
});

function handleSeat(seatNode) {
	if (seatNode.hasClass('seat-free')) {
		seatNode.addClass('seat-marked');
	}
}
