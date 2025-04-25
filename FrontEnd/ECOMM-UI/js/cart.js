//Global Use.
// JS is making use of localStorage here
// Note: localStorage only stores strings
// Retrieve cart from localStorage; if not found, initialize as an empty array.
let cart = JSON.parse(localStorage.getItem("cart")) || [];

// Load cart items into table
function loadCart() {
  // Refresh global cart from localStorage
  cart = JSON.parse(localStorage.getItem("cart")) || [];

  let cartItems = document.getElementById("cart-items");
  let totalAmount = 0;
  cartItems.innerHTML = ""; //  Clears the table first

  // Loop through each item in cart
  cart.forEach((item, index) => {
    let itemTotal = item.price * item.quantity;
    totalAmount += itemTotal;
     
    // (+=) That means you're adding new rows without removing the old ones.
    cartItems.innerHTML += `
      <tr>
        <td><img src="${item.imgUrl}" width="50"></td>
        <td>${item.name}</td>
        <td>${item.price}</td>
        <td>
          <button class="btn btn-sm btn-secondary" onclick="changeQuantity(${index}, -1)">-</button>
          ${item.quantity}
          <button class="btn btn-sm btn-secondary" onclick="changeQuantity(${index}, 1)">+</button>
        </td>
        <td>₹ ${itemTotal}</td>
        <td><button class="btn btn-danger btn-sm" onclick="removeFromCart(${index})">X</button></td>
      </tr>
    `;
  });

  //  Make sure this is outside the loop so totalAmount is correctly shown
  // if we will put it inside the loop the last amount will be shown even after the removal of item from cart.
  // that's why we are putting it outside of the loop
  document.getElementById("total-amount").innerText = ` ${totalAmount}`;
}

// Add product to cart
function addToCart(id, name, price, imgUrl) {
  console.log("Adding Product to cart:", id, name, price, imgUrl);

  price = parseFloat(price); // Ensure price is a number

  // Check if product is already in cart
  let itemIndex = cart.findIndex((item) => item.id === id);

  // if the item index not found then the array will be empty.
  if (itemIndex !== -1) {
    cart[itemIndex].quantity += 1;
  } else {
    // we are storing product locally using cart.push() ,method.
    cart.push({
      id: id, // storing id for easy tracking.
      name: name,
      price: price,
      imgUrl: imgUrl,
      quantity: 1, // increment quantity.
    });
  }
  // storing all the data into localStorage  ,, key = "cart"
  localStorage.setItem("cart", JSON.stringify(cart));

  //for Update Cart on font-end
  updateCartCounter();
}

// Update cart item count (badge)
function updateCartCounter() {
  document.querySelector(".cart-badge").innerText = cart.length;
}

// Change quantity of item
function changeQuantity(index, change) {
  // here we are parsing JSON data into object and if we got data from the local storage then hold it onto cart
  // else return empty array.
  cart = JSON.parse(localStorage.getItem("cart")) || [];
  
  // cart[index].quantity accesses the quantity of the item at position index in the cart array.
  // and change the quantity of the item of the current Index.
  cart[index].quantity += change;

  // If quantity is 0 or less, remove item
  // here splice(index , 1) : it will remove  element from array that would be at currIndex.
  // if the quantity become <= 0 slice() automatically remove the item from cart.
  //Preventing users from seeing items they already removed.
  if (cart[index].quantity <= 0) {
    cart.splice(index, 1);
  }
  // set the change into local storage
  //JSON.stringify() helps convert data into a string format
  localStorage.setItem("cart", JSON.stringify(cart));

  // load the data into cart by calling loadCart()
  loadCart();

  // update cart Counter by calling below method()
  updateCartCounter();
}

// Remove item directly from cart
function removeFromCart(index) {
  cart = JSON.parse(localStorage.getItem("cart")) || [];
  cart.splice(index, 1); // Remove item from cart
  localStorage.setItem("cart", JSON.stringify(cart));
  loadCart();
  updateCartCounter();
}

// Run on page load
// after loading JS
// as soon as the DOMContect loaded we will run loadCart() method.
document.addEventListener("DOMContentLoaded", loadCart);
