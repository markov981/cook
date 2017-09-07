const baseurl = 'http://localhost:22229/recipes';

// Click-handler for the entire page
$(document).on('click', 'a[data-recipe-id]', function (e){
	e.preventDefault();
	//console.log(this);   // this is what causes the event
	// data-recipe-id  --> data(recipeId) - camel case for 'Id' and no '_'
	const recipeId = $(this).data('recipeId');
	$.getJSON(baseurl + '/' + recipeId, function(data){
		data.description = data.description || 'no description specified';
		$('#detail') 
		    .html(`
		    		<h1>${data.recipeId} ${data.name}</h1>
		    		<h2>${data.description}</h2>
		    		<div>Title: ${data.description}</div>
		         `)
				 
		
		
	});
	
});




// Load JSON from API
$.getJSON(baseurl, function(data){
	if(data.length){
		for(let recipe of data) {
			$('<li></li>')
			.html('<a href="#" data-recipe-id="'  + recipe.id  +  '">' + recipe.title + '</a>')
			.appendTo($('#list'));			
		}		
	}else{
		 $('<li></li>')
		   .css('color', 'red')
		   .html('You have no data')
		   .appendTo($('#list'));
	}
});