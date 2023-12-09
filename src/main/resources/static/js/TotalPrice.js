let subtotal = 0;

document.querySelectorAll('.price').forEach(priceElement => {
    let product_price = parseFloat(priceElement.innerText.replace('$', ''));

    subtotal = subtotal + product_price;
});

document.getElementById('subtotal').innerText = '$' + Math.round(subtotal * 100) /100;

let shipping = 10.00;

let totalPrice = subtotal + shipping;

document.getElementById('total').innerText = '$' + Math.round(totalPrice * 100) /100
document.getElementById('totalAmount').innerText = '$' + Math.round(totalPrice * 100) /100;
