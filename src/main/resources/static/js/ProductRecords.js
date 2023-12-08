document.querySelectorAll('.product-edit-btn').forEach(btn => {
    btn.addEventListener('click', editProductHandler);
});

function editProductHandler(event) {
    const row = event.target.closest('tr');

    // Get the elements within the row
    const nameElement = row.querySelector('.name');
    const descriptionElement = row.querySelector('.description');
    const categoryElement = row.querySelector('.category');
    const priceElement = row.querySelector('.price');
    const quantityElement = row.querySelector('.quantity');
    const skuElement = row.querySelector('.sku');
    const actionsElement = row.querySelector('.actions');

    // Create input elements with the same values as the current text
    const nameInput = createInput('text', 'name', nameElement.textContent);
    const descriptionInput = createInput('text', 'description', descriptionElement.textContent);
    const categoryInput = createCategorySelect(categoryElement.textContent);
    const priceInput = createInput('text', 'price', priceElement.textContent);
    const quantityInput = createInput('text', 'quantity', quantityElement.textContent);
    const skuInput = createInput('text', 'sku', skuElement.textContent);

    // Create Save and Undo buttons
    const saveProductButton = createButton('button', 'save-product-btn', 'Save', saveProductHandler);
    const undoProductButton = createButton('button', 'undo-product-btn', 'Undo', undoHandler);

    // Replace text with inputs
    replaceElement(nameElement, nameInput);
    replaceElement(descriptionElement, descriptionInput);
    replaceElement(categoryElement, categoryInput);
    replaceElement(priceElement, priceInput);
    replaceElement(quantityElement, quantityInput);
    replaceElement(skuElement, skuInput);

    // Replace Edit and Delete buttons with Save and Undo buttons
    replaceBtn(actionsElement.querySelector('.product-edit-btn'), saveProductButton);
    replaceBtn(actionsElement.querySelector('.delete-btn'), undoProductButton);

    saveProductButton.classList.add('btn', 'btn-success', 'btn-sm');
    undoProductButton.classList.add('btn', 'btn-dark', 'btn-sm');
    nameInput.classList.add('w-100');
    descriptionInput.classList.add('w-100');
    categoryInput.classList.add('form-select', 'w-100');
    priceInput.classList.add('w-100');
    quantityInput.classList.add('w-100');
    skuInput.classList.add('w-100');
}

function createCategorySelect(currentCategory) {
    const select = document.createElement('select');
    select.name = 'category';
    select.classList.add('form-select', 'w-100');

    // Assuming you have a list of available categories
    const availableCategories = ['FOOD_SUPPLEMENT', 'DRINKS', 'CLOTHES', 'GYM_EQUIPMENT'];

    availableCategories.forEach(category => {
        const option = document.createElement('option');
        option.value = category;
        option.text = category;
        if (currentCategory === category) {
            option.selected = true;
        }
        select.appendChild(option);
    });

    return select;
}

function createInput(type, name, value) {
    const input = document.createElement('input');
    input.type = type;
    input.name = name;
    input.value = value;
    return input;
}

function createButton(type, id, content, clickHandler) {
    const button = document.createElement('button');
    button.type = type;
    button.id = id;
    button.textContent = content;
    button.addEventListener('click', clickHandler);
    return button;
}

function replaceElement(oldElement, newElement) {
    oldElement.innerHTML = ''; // Clear the contents of the old element
    oldElement.appendChild(newElement); // Append the new element inside the old element
}

function replaceBtn(oldElement, newElement) {
    oldElement.replaceWith(newElement);
}

function saveProductHandler(event) {
    const row = event.target.closest('tr');
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;

    const id = row.querySelector('.id').textContent;
    const name = row.querySelector('.name input').value;
    const description = row.querySelector('.description input').value;
    const category = row.querySelector('.category select').value;
    const price = row.querySelector('.price input').value;
    const quantity = row.querySelector('.quantity input').value;
    const sku = row.querySelector('.sku input').value;

    // Make an AJAX request to update the product
    fetch(`/api/admins/management/products/update/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': csrfToken,
        },
        body: JSON.stringify({
            name: name,
            description: description,
            category: category,
            price: price,
            quantity: quantity,
            sku: sku,
        }),
    })
        .then(response => {
            if (response.ok) {
                // Handle success
                alert(`Product with ID ${id} is updated successfully!`);
                location.reload();
            } else {
                // Handle error
                return response.text().then(errorMessage => {
                    console.error('Error updating product:', errorMessage);
                    alert(`Error updating product with ID ${id}`);
                });
            }
        })
        .catch(error => {
            console.error('Error:', error);
            // Handle errors, e.g., show an alert
            alert(`Error updating product with ID ${id}`);
        });
}

function undoHandler() {
    location.reload();
}
