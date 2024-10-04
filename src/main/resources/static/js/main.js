(function ($) {
"use strict";
// TOP Menu Sticky
$(window).on('scroll', function () {
  var scroll = $(window).scrollTop();
  if (scroll < 200) {
    $("#sticky-header").removeClass("sticky");
    $('#back-top').fadeIn(500);
  } else {
    $("#sticky-header").addClass("sticky");
    $('#back-top').fadeIn(500);
  }
});
$(document).ready(function(){

// review-active
$('.slider_active').owlCarousel({
  loop:true,
  margin:0,
items:1,
autoplay:true,
navText:['<i class="fa fa-long-arrow-left"></i>','<i class="fa fa-long-arrow-right"></i>'],
  nav:true,
dots:false,
autoplayHoverPause: true,
autoplaySpeed: 600,
  responsive:{
      0:{
          items:1,
          nav:false,
      },
      767:{
          items:1,
          nav:false,
      },
      992:{
          items:1,
          nav:false
      },
      1200:{
          items:1,
          nav:false
      },
      1600:{
          items:1,
          nav:true
      }
  }
});

// testi slider
      $('.testi-carousel').owlCarousel({
            loop:true,
            margin:30,
            dots:true,
            nav: true,
      center: true,
            responsiveClass:true,
            navText: [
               "<i class='fa fa-angle-left effect-1'></i>",
               "<i class='fa fa-angle-right effect-1'></i>"
            ],
            responsive:{
                0:{
                    items:1,
                    nav:false
                },
                600:{
                    items:1,
                    nav:false
                },
                1000:{
                    items:1,
                    loop:false
                },
        1200:{
                    items:1,
                    loop:false
                }
            }
        })


       
    {
        if($('.courses_slider').length)
        {
            var coursesSlider = $('.courses_slider');
            coursesSlider.owlCarousel(
            {
                items:3,
                loop:true,
                autoplay:true,
                autoplayTimeout:8000,
                nav:false,
                dots:false,
                smartSpeed:1200,
                margin:30,
                responsive:
                {
                    0:
                    {
                        items:1
                    },
                    768:
                    {
                        items:2
                    },
                    992:
                    {
                        items:3
                    }
                }
            });

            if($('.courses_slider_prev').length)
            {
                var prev = $('.courses_slider_prev');
                prev.on('click', function()
                {
                    coursesSlider.trigger('prev.owl.carousel');
                });
            }

            if($('.courses_slider_next').length)
            {
                var next = $('.courses_slider_next');
                next.on('click', function()
                {
                    coursesSlider.trigger('next.owl.carousel');
                });
            }
        }
    }

});


  /*-------------------------------------
      Sidebar Toggle Menu
    -------------------------------------*/
  $('.sidebar-toggle-view').on('click', '.sidebar-nav-item .nav-link', function (e) {
    if (!$(this).parents('#wrapper').hasClass('sidebar-collapsed')) {
      var animationSpeed = 200,
        subMenuSelector = '.sub-group-menu',
        $this = $(this),
        checkElement = $this.next();
      if (checkElement.is(subMenuSelector) && checkElement.is(':visible')) {
        checkElement.slideUp(animationSpeed, function () {
          checkElement.removeClass('menu-open');
        });
        checkElement.parent(".sidebar-nav-item").removeClass("active");
      } else if ((checkElement.is(subMenuSelector)) && (!checkElement.is(':visible'))) {
        var parent = $this.parents('ul').first();
        var ul = parent.find('ul:visible').slideUp(animationSpeed);
        ul.removeClass('menu-open');
        var parent_li = $this.parent("li");
        checkElement.slideDown(animationSpeed, function () {
          checkElement.addClass('menu-open');
          parent.find('.sidebar-nav-item.active').removeClass('active');
          parent_li.addClass('active');
        });
      }
      if (checkElement.is(subMenuSelector)) {
        e.preventDefault();
      }
    } else {
      if ($(this).attr('href') === "#") {
        e.preventDefault();
      }
    }
  });

  /*-------------------------------------
      Sidebar Menu Control
    -------------------------------------*/
  $(".sidebar-toggle").on("click", function () {
    window.setTimeout(function () {
      $("#wrapper").toggleClass("sidebar-collapsed");
    }, 500);
  });

  /*-------------------------------------
      Sidebar Menu Control Mobile
    -------------------------------------*/
  $(".sidebar-toggle-mobile").on("click", function () {
    $("#wrapper").toggleClass("sidebar-collapsed-mobile");
    if ($("#wrapper").hasClass("sidebar-collapsed")) {
      $("#wrapper").removeClass("sidebar-collapsed");
    }
  });

 /*-------------------------------------
          Calender initiate 
      -------------------------------------*/
    if ($.fn.fullCalendar !== undefined) {
      $('#fc-calender').fullCalendar({
        header: {
          center: 'basicDay,basicWeek,month',
          left: 'title',
          right: 'prev,next',
        },
        fixedWeekCount: false,
        navLinks: true, // can click day/week names to navigate views
        editable: true,
        eventLimit: true, // allow "more" link when too many events
        aspectRatio: 1.8,
        events: [{
            title: 'All Day Event',
            start: '2019-04-01'
          },

          {
            title: 'Meeting',
            start: '2019-04-12T14:30:00'
          },
          {
            title: 'Happy Hour',
            start: '2019-04-15T17:30:00'
          },
          {
            title: 'Birthday Party',
            start: '2019-04-20T07:00:00'
          }
        ]
      });
    }
      if ($.fn.select2 !== undefined) {
      $('.select2').select2({
        width: '100%'
      });
    }
// mobile_menu
var menu = $('ul#navigation');
if(menu.length){
  menu.slicknav({
    prependTo: ".mobile_menu",
    closedSymbol: '+',
    openedSymbol:'-'
  });  
};
// scroll indicator

// When the user scrolls the page, execute myFunction
window.onscroll = function() {myFunction()};

function myFunction() {
  var winScroll = document.body.scrollTop || document.documentElement.scrollTop;
  var height = document.documentElement.scrollHeight - document.documentElement.clientHeight;
  var scrolled = (winScroll / height) * 100;
  document.getElementById("myBar").style.width = scrolled + "%";
}

})(jQuery); 