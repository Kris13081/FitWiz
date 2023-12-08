document.querySelectorAll('.add-to-cart-btn').forEach(btn => {
    btn.addEventListener('click', addToCartHandler);
});


function addToCartHandler(event) {

    const productSKU = event.currentTarget.id;
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;

    // Send a POST request to add the product to the cart
    fetch(`/api/store/cart/add/${productSKU}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': csrfToken,
        },
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json(); // or response.text() if the response is not JSON
        })
        .then(data => {
            console.log("OK"); // Handle the successful response
        })
        .catch(error => {
            console.error('Error:', error); // Handle errors
        });
}
