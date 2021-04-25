$(document).ready(function () {
    $(".select").select2({
        placeholder: "Select an option",
        allowClear: true
    });

    $('.select').on('change', function() {
        switch (this.value){
            case 'navigator':{
                openNav();
                break;
            }
            case 'qr':{
                openModal();
                break;
            }
            default:break;
        }
    });

});