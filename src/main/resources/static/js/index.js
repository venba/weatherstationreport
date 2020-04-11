"use strict";
function onSubmit() {
	// get instance of all form controls
	var form = document.getElementById("stations-list");
	var startDate = document.getElementById("startDate");
	var startDateErrorMessage = document
			.getElementById("startDateErrorMessage");
	var startDateLabel = document.getElementById("startDateLabel");
	var endDate = document.getElementById("endDate");
	var endDateErrorMessage = document.getElementById("endDateErrorMessage");
	var endDateLabel = document.getElementById("endDateLabel");

	// clean up any existing messages
	startDateErrorMessage.innerHTML = "";
	endDateErrorMessage.innerHTML = "";

	// validate required fields
	if (startDate.value.length === 0 || endDate.value.length === 0) {
		if (startDate.value.length === 0) {
			startDateErrorMessage.innerHTML = "Start date is required";
		}
		if (endDate.value.length === 0) {
			endDateErrorMessage.innerHTML = "End date is required";
		}
		return false;
	}

	// validate date range
	if (startDate.value && endDate.value) {
		if (new Date(startDate.value) > new Date(endDate.value)) {
			endDateErrorMessage.innerHTML = "End date must be greater or equals to start date";
			return false
		}
	}
	// Stop form from submitting normally
	event.preventDefault();

	var posting = $.post("/filter", $("#stations-list").serialize());

	// Put the results in a div
	posting.done(function(data) {
		$("#stations").empty().append(data);
	});
}

// reload the page to get initial data
function onReset(){
    window.location.href = window.location.href;
}