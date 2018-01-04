/**
 * Setting specific handlers and effects for Main View page.
 * Creating count down function to refresh page periodically.
 *
 * @author sstreltsov
 */
$(function() {
  SetupComponentsView();
});

function SetupComponentsView() {
  $( ".controls" ).sortable({
    revert: true
  });

  $( "ul, li" ).disableSelection();

  $(".box ul.controls li").on({
    mouseenter: function(){
      $(this).find(".control-tooltip").show().animate({
        bottom: '34',
        opacity: 0.9
      }, 200,function(){
        $(".control-tooltip").on({
          mouseenter: function(){
            $(this).hide();
          }
        });
      });
    },
    mouseleave: function(){
      $(this).find(".control-tooltip").hide().animate({
        bottom: '28',
        opacity: 0
      }, 200);
    }
  });

  // fitting modal-body size
  $(".modal").each(function() {
    $(this).on("shown", function() {
      fitModalBody($(this));
    });
  });
  $(window).resize(function() {
    $(".modal").each(function() {
      fitModalBody($(this));
    });
  });

  // to prevent background scrolling under modal
  $("[id^='component-modal']").each(function(index) {
    $(this).on("show", function () { // show event seems to appear not only when you open the modal
      body = $("body,html");
      if(!body.hasClass("modal-open")) { // to avoid multiple times addition
        body.addClass("modal-open");
      }
      if(!$(".modal-backdrop", body)) {
        $('<div class="modal-backdrop fade in" style="z-index: 1040;"></div>').appendTo(body);
      }
    }).on("hidden", function () {
      $("body,html").removeClass("modal-open");
      $(".modal-backdrop").remove();
    });
  });

  // As we have conditional display of component inspection modal tabs
  // we should manually make active first available tab and trigger data load function.
  $('.component-inspection').on('shown.bs.modal', function () {
    $(this).find('.tabs-pane a:first').click();
  });
}
