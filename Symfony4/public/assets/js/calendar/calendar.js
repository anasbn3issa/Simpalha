var newEvent;
var editEvent;
var today = new Date();
var dd = String(today.getDate()).padStart(2, "0");
var mm = String(today.getMonth() + 1).padStart(2, "0"); //January is 0!
var yyyy = today.getFullYear();

today = yyyy + "-" + mm + "-" + dd;
console.log(today);

$(document).ready(function () {

    var calendar = $("#calendar").fullCalendar({
        eventRender: function (event, element, view) {
            var startTimeEventInfo = moment(event.start).format("HH:mm");
            var endTimeEventInfo = moment(event.end).format("HH:mm");
            var displayEventDate;

            if (event.avatar.length > 1) {
                element.find(".fc-content").css("padding-left", "55px");
                element
                    .find(".fc-content")
                    .after(
                        $('<div class="fc-avatar-image"></div>').html(
                            "<img src='" + event.avatar + "' />"
                        )
                    );
            }

            if (event.allDay == false) {
                displayEventDate = startTimeEventInfo + " - " + endTimeEventInfo;
            } else {
                displayEventDate = "All Day";
            }


        },
        header: {
            left: "today, prevYear, nextYear, printButton",
            center: "prev, title, next",
            right: "month,agendaWeek,agendaDay,listWeek",
        },
        views: {
            month: {
                columnFormat: "dddd",
            },
            agendaWeek: {
                columnFormat: "ddd D/M",
                eventLimit: false,
            },
            agendaDay: {
                columnFormat: "dddd",
                eventLimit: false,
            },
            listWeek: {
                columnFormat: "",
            },
        },

        loading: function (bool) {
            //alert('events are being rendered');
        },
        eventAfterAllRender: function (view) {
            if (view.name == "month") {
                $(".fc-content").css("height", "auto");
            }
        },
        eventLimitClick: function (cellInfo, event) {},
        eventResize: function (event, delta, revertFunc, jsEvent, ui, view) {
            $(".popover.fade.top").remove();
        },
        eventDragStart: function (event, jsEvent, ui, view) {
            var draggedEventIsAllDay;
            draggedEventIsAllDay = event.allDay;
        },
        eventDrop: function (event, delta, revertFunc, jsEvent, ui, view) {
            $(".popover.fade.top").remove();
            console.log("event");
            console.log(event);
            console.log("view");
            console.log(view);
        },
        unselect: function (jsEvent, view) {
            //$(".dropNewEvent").hide();
        },
        dayClick: function (startDate, jsEvent, view) {
            //var today = moment();
            //var startDate;
            //if(view.name == "month"){
            //  startDate.set({ hours: today.hours(), minute: today.minutes() });
            //  alert('Clicked on: ' + startDate.format());
            //}
        },
        select: function (startDate, endDate, jsEvent, view) {
            var today = moment();
            var startDate;
            var endDate;

            if (view.name == "month") {
                startDate.set({ hours: today.hours(), minute: today.minutes() });
                startDate = moment(startDate).format("ddd DD MMM YYYY HH:mm");
                endDate = moment(endDate).subtract("days", 1);
                endDate.set({ hours: today.hours() + 1, minute: today.minutes() });
                endDate = moment(endDate).format("ddd DD MMM YYYY HH:mm");
            } else {
                startDate = moment(startDate).format("ddd DD MMM YYYY HH:mm");
                endDate = moment(endDate).format("ddd DD MMM YYYY HH:mm");
            }

            var $contextMenu = $("#contextMenu");

            var HTMLContent =
                '<ul class="dropdown-menu dropNewEvent" role="menu" aria-labelledby="dropdownMenu" style="display:block;position:static;margin-bottom:5px;">' +
                "<li onclick='newEvent(\"" +
                startDate +
                '","' +
                endDate +
                '","' +
                "Appointment" +
                '")\'> <a tabindex="-1" href="#">Add Appointment</a></li>' +
                "<li onclick='newEvent(\"" +
                startDate +
                '","' +
                endDate +
                '","' +
                "Check-in" +
                '")\'> <a tabindex="-1" href="#">Add Check-In</a></li>' +
                "<li onclick='newEvent(\"" +
                startDate +
                '","' +
                endDate +
                '","' +
                "Checkout" +
                '")\'> <a tabindex="-1" href="#">Add Checkout</a></li>' +
                "<li onclick='newEvent(\"" +
                startDate +
                '","' +
                endDate +
                '","' +
                "Inventory" +
                '")\'> <a tabindex="-1" href="#">Add Inventory</a></li>' +
                "<li onclick='newEvent(\"" +
                startDate +
                '","' +
                endDate +
                '","' +
                "Valuation" +
                '")\'> <a tabindex="-1" href="#">Add Valuation</a></li>' +
                "<li onclick='newEvent(\"" +
                startDate +
                '","' +
                endDate +
                '","' +
                "Viewing" +
                '")\'> <a tabindex="-1" href="#">Add Viewing</a></li>' +
                '<li class="divider"></li>' +
                '<li><a tabindex="-1" href="#">Close</a></li>' +
                "</ul>";

            $(".fc-body").unbind("click");
            $(".fc-body").on("click", "td", function (e) {

                newEvent(startDate + '","' + endDate + '","' + "Appointment");
                return false;
            });

            $contextMenu.on("click", "a", function (e) {
                e.preventDefault();
                $contextMenu.removeClass("contextOpened");
                $contextMenu.hide();
            });

            $("body").on("click", function () {
                $contextMenu.hide();
                $contextMenu.removeClass("contextOpened");
            });

            //newEvent(startDate, endDate);
        },
        locale: "en-GB",
        timezone: "local",
        nextDayThreshold: "09:00:00",
        allDaySlot: false,
        eventStartEditable: false,
        displayEventTime: true,
        displayEventEnd: true,
        firstDay: 0,
        weekNumbers: false,
        selectable: true,
        weekNumberCalculation: "ISO",
        eventLimit: true,
        eventLimitClick: "week", //popover
        navLinks: true,
        defaultDate: moment(today),
        timeFormat: "HH:mm",
        defaultTimedEventDuration: "01:00:00",
        editable: false,
        minTime: "00:00:00",
        maxTime: "23:00:00",
        slotLabelFormat: "HH:mm",
        weekends: true,
        nowIndicator: true,
        dayPopoverFormat: "dddd DD/MM",
        longPressDelay: 0,
        eventLongPressDelay: 0,
        selectLongPressDelay: 0,

        events: events,
        eventClick: function (event, jsEvent, view) {
            editEvent(event);
            console.log(event);
        }
    });

    $(".search").on("input", function () {
        $data = $(this).val();
        if ($data == "") {
            $("#calendar").fullCalendar("changeView", defaultCalendarView);
            $("#calendar").fullCalendar("removeEvents");
            //fillCalendar();
            $("#calendar").fullCalendar("renderEvents", events, false);
        } else {
            $.ajax({
                type: "GET",
                url: filterpath+'?filter='+$data,
                dataType: "json",
                success: function (data) {
                    if (data.status === "ERROR") {
                        $("#calendar").fullCalendar("removeEvents");
                        $("#calendar").fullCalendar("changeView", defaultCalendarView);
                        //$("#calendar").fullCalendar("renderEvents", events, false);
                    } else {
                        $("#calendar").fullCalendar("removeEvents");
                        var evts = [];
                        $("#calendar").fullCalendar("changeView", "listWeek");
                        var meets= data.data;
                        for (var i = 0; i < meets.length; i++) {
                            evts.push({
                                _id: meets[i].id,
                                title: 'Helper '+ meets[i].idHelper.username + " meet with "+meets[i].idStudent.username,
                                avatar: "",
                                etat:meets[i].etat,
                                description:
                                    'Helper '+ meets[i].idHelper.username + " meet with "+meets[i].idStudent.username,
                                start: moment(meets[i].disponibilite.datedeb),
                                end: moment(meets[i].disponibilite.datefin),
                                type: "Appointment",
                                calendar: "",
                                className: "colorAppointment",
                                student: meets[i].idStudent.username,
                                specialite: meets[i].specialite,
                                helper: meets[i].idHelper.username,
                                backgroundColor: "#f4516c",
                                textColor: "#ffffff",
                                allDay: false,
                            });
                        }
                        $("#calendar").fullCalendar("renderEvents", evts, false);
                    }
                },
                error: function (data) {
                    console.log("error");
                    console.log(data.responseText);
                },
            });
        }
    });

    $(".filter").on("change", function () {
        $("#calendar").fullCalendar("rerenderEvents");
    });

   /* $("#type_filter").select2({
        placeholder: "Filter Types",
        allowClear: true,
    });

    $("#calendar_filter").select2({
        placeholder: "Filter Calendars",
        allowClear: true,
    });*/

    //CREATE NEW EVENT CALENDAR

    newEvent = function (start) {
        var colorEventyType = "colorAppointment";
        var startTimeEventInfo = moment(start.split(",")[0].slice(0, -1)).format(
            "YYYY-MM-DDTHH:mm:ss"
        );
        var endTimeEventInfo = moment(start.split(",")[1].slice(0, -1)).format(
            "YYYY-MM-DDTHH:mm:ss"
        );

        $("#contextMenu").hide();
        $(".eventType").text(start.split(",")[2].slice(0, -1));
        $("input#title").val("");
        $("#starts-at").val(startTimeEventInfo);
        $("#ends-at").val(endTimeEventInfo);
        $("#ends-at").attr("min", endTimeEventInfo);

        $("#newEventModal").modal("show");

        var statusAllDay;
        var endDay;

        $(".allDayNewEvent").on("change", function () {
            if ($(this).is(":checked")) {
                statusAllDay = true;
                var endDay = $("#ends-at").prop("disabled", true);
            } else {
                statusAllDay = false;
                var endDay = $("#ends-at").prop("disabled", false);
            }
        });

        //GENERATE RAMDON ID - JUST FOR TEST - DELETE IT
        var eventId = 1 + Math.floor(Math.random() * 1000);
        //GENERATE RAMDON ID - JUST FOR TEST - DELETE IT

    };

    //EDIT EVENT CALENDAR

    editEvent = function (event, element, view) {
        $(".popover.fade.top").remove();
        $("#helperIdtext").empty().append(event.helper);
        $("#studentIdtext").empty().append(event.student);
        $("#specialiteIdtext").empty().append(event.specialite);

        $("#datetext").empty().append(moment(event.start).format("MMM. D, YYYY [at] h:mm A z"));
        var status = "New";
        if(event.etat!="0")
            status = "Finished";
        $("#statustext").empty().append(status);



        $("#editEventModal").modal("show");
        $("#updateEvent").unbind();

    };

    //SET DEFAULT VIEW CALENDAR

    var defaultCalendarView = "agendaDay";

    if (defaultCalendarView == "month") {
        $("#calendar").fullCalendar("changeView", "month");
    } else if (defaultCalendarView == "agendaWeek") {
        $("#calendar").fullCalendar("changeView", "agendaWeek");
    } else if (defaultCalendarView == "agendaDay") {
        $("#calendar").fullCalendar("changeView", "agendaDay");
    } else if (defaultCalendarView == "listWeek") {
        $("#calendar").fullCalendar("changeView", "listWeek");
    }

    $("#calendar_view").on("change", function () {
        var defaultCalendarView = $("#calendar_view").val();
        $("#calendar").fullCalendar("changeView", defaultCalendarView);
    });

    //SET MIN TIME AGENDA

    $("#calendar_start_time").on("change", function () {
        var minTimeAgendaView = $(this).val();
        $("#calendar").fullCalendar("option", { minTime: minTimeAgendaView });
    });

    //SET MAX TIME AGENDA

    $("#calendar_end_time").on("change", function () {
        var maxTimeAgendaView = $(this).val();
        $("#calendar").fullCalendar("option", { maxTime: maxTimeAgendaView });
    });

    //SHOW - HIDE WEEKENDS

    var activeInactiveWeekends = false;
    //checkCalendarWeekends();

    $(".showHideWeekend").on("change", function () {
        //checkCalendarWeekends();
    });

    //SHOW - POPULATE CALENDAR
    //fillCalendar();

    function checkCalendarWeekends() {
        if ($(".showHideWeekend").is(":checked")) {
            activeInactiveWeekends = true;
            $("#calendar").fullCalendar("option", {
                weekends: activeInactiveWeekends,
            });
        } else {
            activeInactiveWeekends = false;
            $("#calendar").fullCalendar("option", {
                weekends: activeInactiveWeekends,
            });
        }
    }

    //CREATE NEW CALENDAR AND APPEND

    $("#addCustomCalendar").on("click", function () {
        var newCalendarName = $("#inputCustomCalendar").val();
        $("#calendar_filter, #calendar-type, #edit-calendar-type").append(
            $("<option>", {
                value: newCalendarName,
                text: newCalendarName,
            })
        );
        $("#inputCustomCalendar").val("");
    });

    //WEATHER GRAMATICALLY

    function retira_acentos(str) {
        var com_acento =
            "¿¡¬√ƒ≈∆«»… ÀÃÕŒœ–—“”‘’÷ÿŸ⁄€‹›Rﬁﬂ‡·‚„‰ÂÊÁËÈÍÎÏÌÓÔÒÚÛÙıˆ¯˘˙˚¸˝˛ˇr";
        var sem_acento =
            "AAAAAAACEEEEIIIIDNOOOOOOUUUUYRsBaaaaaaaceeeeiiiionoooooouuuuybyr";
        var novastr = "";
        for (i = 0; i < str.length; i++) {
            troca = false;
            for (a = 0; a < com_acento.length; a++) {
                if (str.substr(i, 1) == com_acento.substr(a, 1)) {
                    novastr += sem_acento.substr(a, 1);
                    troca = true;
                    break;
                }
            }
            if (troca == false) {
                novastr += str.substr(i, 1);
            }
        }
        return novastr.toLowerCase().replace(/\s/g, "-");
    }
});
