// Enable Carousel Controls
$('.carousel-control-prev').click(function () {
    $('#recipeCarousel').carousel('prev');
});

$('.carousel-control-next').click(function () {
    $('#recipeCarousel').carousel('next');
});

// Manually trigger carousel initialization after the page is loaded
$('#recipeCarousel').carousel();
