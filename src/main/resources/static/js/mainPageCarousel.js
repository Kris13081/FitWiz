document.addEventListener("DOMContentLoaded", function () {
    var myCarousel = new bootstrap.Carousel(document.getElementById('carouselBasicExample'), {
        interval: 10000,
        keyboard: false
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