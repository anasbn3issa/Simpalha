$(document).ready(function() {
    $('.js-mark-as-solution').on('click', function(e) {
        e.preventDefault();

        var $link = $(e.currentTarget);

        $.ajax({
            method: 'POST',
            url: $link.attr('href')
        }).done(function(data) {

        })
    });
});
