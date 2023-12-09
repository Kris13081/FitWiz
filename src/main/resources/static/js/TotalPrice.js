let subtotal = 0;

document.querySelectorAll('.price').forEach(priceElement => {
    let product_price = parseFloat(priceElement.innerText.replace('$', ''));

    subtotal = subtotal + product_price;
});

document.getElementById('subtotal').innerText = '$' + subtotal;

let shipping = 10.00;

let totalPrice = subtotal + shipping;

document.getElementById('total').innerText = '$' + totalPrice
document.getElementById('totalAmount').innerText = '$' + totalPrice;
