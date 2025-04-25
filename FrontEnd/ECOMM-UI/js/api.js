// Base URL
const BASE_URL = "http://localhost:8080";

async function loadProducts() {
  try {
    // Backtics(``) : In JS if we want to write something in String format then we use Backticks.
    // when the data will come after aclling this API.
    // we are putting this data response & making it constant so that the data does not change.
    const response = await fetch(`${BASE_URL}/products`);

    // converting response to JSON format & holding it into products.
    // and we are waiting(await()) the data does not convert into JSON.
    const products = await response.json();
    console.log(products);

    // to showing data we will use getElementById
    let trendingList = document.getElementById("trending-products");
    let clothingList = document.getElementById("clothing-products");
    let electronicsList = document.getElementById("electronics-products");

    // putting something into  above variables. for now we are putting empty list.
    trendingList.innerHTML = "";
    clothingList.innerHTML = "";
    electronicsList.innerHTML = "";

    // putting for each loop onto each products
    products.forEach((product) => {
      // making card of the data we will receive in JSON format
      // and by this we will check where the data will go (clothing , trending , electronics)
      let productCard = `
               <div class="col-lg-4 col-md-6">
                  <div class="card h-100">
                      <img src="${product.imgUrl}" class="card-img-top" alt="${product.name}">
                      <div class="card-body d-flex flex-column">
                          <h5 class="card-title">${product.name}</h5>
                          <p class="card-text">${product.description}</p>
                          <p class="price"><strong>₹${product.price}</strong></p>
                          <button  class="btn btn-primary mt-auto"
                          onclick="addToCart(${product.id} , '${product.name}' ,${product.price},'${product.imgUrl}')">
                          Add to Cart
                          </button>
                      </div>
                  </div>
               </div>
        `;

      // Attach Card into categories by .
      // if the category comes clothing then we put this productCard into clothingList
      if (product.category === "Clothing") {
        clothingList.innerHTML += productCard;
      }
      // if the category comes Electronics then put this into ElectronicLits
      else if (product.category === "Electronics") {
        electronicsList.innerHTML += productCard;
      }

      // if the category comes others then put this into trendingLits
      else {
        trendingList.innerHTML += productCard;
      }
    });
  } catch (error) {
    // if the error arise we will show it to console
    // showing the error  to console.
    console.log("Error fething Products :", error);
  }
}
