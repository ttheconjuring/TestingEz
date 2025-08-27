$(document).ready(function () {
    $(".flag").click(function () {
        let selectedOption = $(this).attr('id');
        if (selectedOption !== '') {
            window.location.replace('/?lang=' + selectedOption);
        }
    });
});