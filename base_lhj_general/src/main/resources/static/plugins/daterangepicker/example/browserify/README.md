$(document).ready(function() {

    $('#config-text').keyup(function() {
      eval($(this).val());
    });
    
    $('.configurator input, .configurator select').change(function() {
