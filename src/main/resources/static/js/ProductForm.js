function handleFormResponse(response) {
    if (response.status === 201) {
        // Banner created successfully, show success alert
        alert("Product created successfully!");
        location.reload(); // Reload the page
    } else if (response.status === 302) {
        // Banner with the same name already exists, show alert
        alert("Product with this name already exists!");
    } else {
        // Other errors, show alert with the error message
        alert("Failed to create product: " + response.statusText);
    }
}

// Attach an event listener to the form for handling the response
document.getElementById("productForm").addEventListener("submit", function (event) {
    event.preventDefault();
    fetch(event.target.action, {
        method: "POST",
        body: new FormData(event.target),
    }).then(function (response) {
        handleFormResponse(response);
    });
});
