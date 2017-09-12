const baseurl = 'http://localhost:22229/recipes';



function fillInDetails(data){ 
	
    let html = `
        <h4>${data.recipeId} ${data.title}</h4>
        <h6>${data.description}</h6>
        <div>Title: ${data.title}</div>
        
		<ul id="recipe-instructions">
    	`;
    
    
    // Instructions
	for (let instruction of data.instructions)	{
		html += ` 
			<li>${instruction.description}</li>
		`;
	 }
	html += `	
		</ul>        	
	        <form  id="create-instruction-form"  method="post"  action = "/recipes/${data.id}/instructions">
	           <input required   name="description"   id="instruction-description"    placeholder="Instructions">
	           <br>
	           <button>Add Instructions</button>
	        </form>     
		<ul id="ingredients-list">
	`;
	
	
	// Ingredients
	for (let ingredient of data.ingredients)	{
		html += `
			<li>
				<div>${ingredient.food_name}</div>
				<div>${ingredient.units}</div>
				<div>${ingredient.quantity}</div>
			</li>        	
		`;
		//console.log(data);
	}
	html += `	
		</ul>
	            <form id="create-ingredient-form" method="post" action = "/recipes/${data.id}/ingredients">
		            <input required name="food_name" 	id="food_name"  placeholder="food name">
		            <input required name="units" 		id="units"  	placeholder="Units">
		            <input required name="quantity" 	id="quantity"  	placeholder="Quantity">
		        <br>
		            <button>Add Ingredients</button>
	            </form>        
	`;
	$('#recipe-detail').html(html); 	
}


function createListElement(recipe){
        $('<li></li>')
        .html(`
            <a href="#" data-recipe-id="${recipe.id}">
                ${recipe.title}, ${recipe.description}
            </a>
            
            <form class="delete-card-form"   method="post"   action="/recipes/${recipe.id}">
                <button>Delete</button>
            </form>
        `)
        .appendTo($('#recipe-list'));
}



// Instructions - create 
$(document).on('submit', '#create-instruction-form', function (e) {
	e.preventDefault();
	
    let payload ={
            description: $('#instruction-description').val()
        }
    
    let ajaxOptions = {
    	       type: 'POST',
    	       data: JSON.stringify(payload),
    	       contentType: 'application/json'
    	   }    
    
    $.ajax(this.action, ajaxOptions)
         .done(function (data) {       	 
        	 //fillInDetails(data);
        	 $('<li></li>')
     	 	.html(`
				<div>${data.description}</div> 
     	 	`)
     	 	.appendTo('#ingredients-list');         	 
         })
         .fail(error => console.log(error));
});



// Ingredient - create 
$(document).on('submit', '#create-ingredient-form', function (e) {
	e.preventDefault();
	
    let payload = {
            food_name: $('#food_name').val(),
            units: $('#units').val(),
            quantity: $('#quantity').val()
        }
    
    let ajaxOptions = {
    	       type: 'POST',
    	       data: JSON.stringify(payload),
    	       contentType: 'application/json'
    	   }    
    
    // NB
    $.ajax(this.action, ajaxOptions)
         .done(function (data) {
        	 $('<li></li>')
        	 	.html(`
					<div>${data.food_name}</div> 
					<div>${data.units}</div>     
					<div>${data.quantity}</div>
        	 	`)
        	 	.appendTo('#instructions-list');       	 	
         })
         .fail(error => console.log(error));
});







$(document).on('submit', '.delete-card-form', function (e) {
	e.preventDefault();
	
	$.ajax(this.action, {type: 'DELETE'})
		.done(() =>{
			$(this)
				.closest('li')
				.remove();			
		})
		.fail(error => console.log(error));
});


$('#create-recipe').on('submit', function (e) {
    e.preventDefault();

    let payload ={
        title:       $('#title').val(),
        description: $('#description').val()
    }

   let ajaxOptions = {
       type: 'POST',
       data: JSON.stringify(payload),
       contentType: 'application/json'
   }

   $.ajax(this.action, ajaxOptions)
        .done(function (data) {
            createListElement(data) 
        })
        .fail(error => console.error(error));
});



$(document).on('click', 'a[data-recipe-id]', function (e) {
    e.preventDefault();
    const recipeId= $(this).data('recipeId');
   
    $.getJSON(baseurl + '/' + recipeId, function (data) {
        data.recipe = data.recipe || '<i>no recipe specified</i>';  //default operator pattern
        fillInDetails(data);             
    });   
});



$.getJSON(baseurl, function(data) {
    console.log('I got some data:', data);
    if (data.length) { 
        for (let recipe of data)  {
           createListElement(recipe)       
        }        
    } else {
        $('<li></li>')
        .css('color', 'red')
            .html('You have no data.')
            .appendTo($('#recipe-list'));
            
    }
});



