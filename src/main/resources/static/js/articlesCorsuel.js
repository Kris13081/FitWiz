    document.addEventListener("DOMContentLoaded", function () {
    var myCarousel = new bootstrap.Carousel(document.getElementById('carouselExampleIndicators2'), {
});

    var nextButton = document.querySelector('.carousel-control-next');
    nextButton.addEventListener('click', function () {
    myCarousel.next();
});

    var prevButton = document.querySelector('.carousel-control-prev');
    prevButton.addEventListener('click', function () {
    myCarousel.prev();
});
});
