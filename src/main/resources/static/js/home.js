let service;
let directionsService;
let averageRatings = {};
let reviewCounts = {};
let ratingsAndCountsFetched = false;

document.getElementById('search-form').addEventListener('submit', function(event) {
    event.preventDefault();
    searchRestaurants();
});

function initMap() {
    const map = new google.maps.Map(document.getElementById('map'), {
        center: { lat: 35.702332, lng: 139.700003 }, //ジードライブの座標
        zoom: 15
    });
    service = new google.maps.places.PlacesService(map);
    directionsService = new google.maps.DirectionsService();
}

function searchRestaurants() {
	if (!ratingsAndCountsFetched) {
        console.log('Ratings and counts not yet fetched. Aborting search.');
        return;
    }
    
    document.getElementById('sort').value = 'default';
	document.getElementById('info').classList.add('hidden');
    document.getElementById('icon-info').classList.remove('hidden');
	
    const keyword = document.getElementById('keyword').value;
    const genre = document.querySelector('[name="genre"]').value;

    const request = {
        location: { lat: 35.702332, lng: 139.700003 }, //ジードライブの座標
        radius: 100, //お店の検索範囲(半径)
        keyword: genre ? `${keyword} ${genre}` : keyword,
        type: 'restaurant',
    };

    service.nearbySearch(request, (results, status) => {
        if (status === google.maps.places.PlacesServiceStatus.OK) {
            const ul = document.getElementById('restaurant-list');
            ul.innerHTML = '';

            let travelTimeCounter = 0;

            results.forEach((result) => {
                const a = document.createElement('a');
                a.href = `restaurant/${result.place_id}`;
                a.className = 'restaurant-card bg-white p-6 rounded-lg border border-gray-300 mb-1 flex items-center hover:bg-gray-100'; 

                const infoContainer = document.createElement('div');
                infoContainer.className = 'flex-grow';

                const name = document.createElement('span'); 
                name.textContent = result.name;
                name.className = 'block text-lg font-semibold mb-2 -ml-0.5';
                infoContainer.appendChild(name);
                
                if (result.photos && result.photos.length > 0) {
				    const img = document.createElement('img');
				    img.src = result.photos[0].getUrl({ maxWidth: 400, maxHeight: 400 });
				    img.alt = "Restaurant Image";
				    img.className = "w-32 h-32 md:w-36 md:h-36 object-cover mr-4 rounded";
				    a.appendChild(img);
				}
                
				if (averageRatings && averageRatings[result.place_id] !== undefined) {
                    const ratingValue = averageRatings[result.place_id].toFixed(1);
                    a.setAttribute('data-rating', ratingValue);
                                     
                    const timeMarginSpan = document.createElement('span');
				    timeMarginSpan.className = 'text-sm text-gray-600 mr-1';
				    
				    const clockIcon = document.createElement('i');
				    clockIcon.className = 'fas fa-hourglass-start mr-0.5';
				    timeMarginSpan.appendChild(clockIcon);
				    
				    const ratingValueSpan = document.createElement('span');
				    ratingValueSpan.className = 'text-sm text-gray-500 font-semibold';
				    ratingValueSpan.textContent = `${ratingValue} `;
				                        
                    const rating = document.createElement('span');
                    rating.innerHTML = createStars(averageRatings[result.place_id]) + ` (${reviewCounts[result.place_id]})`;
                    rating.className = 'text-sm text-gray-600';
                    
                    infoContainer.appendChild(timeMarginSpan);
                    infoContainer.appendChild(ratingValueSpan);
                    infoContainer.appendChild(rating);
                    
                } else {
                    a.setAttribute('data-rating', '0');
                    
                    const timeMarginSpan = document.createElement('span');
				    timeMarginSpan.className = 'text-sm text-gray-600 mr-1';
				    
				    const clockIcon = document.createElement('i');
				    clockIcon.className = 'fas fa-hourglass-start mr-0.5';
				    timeMarginSpan.appendChild(clockIcon);
                    
                    const rating = document.createElement('span');
                    rating.textContent = '口コミなし';
                    rating.className = 'text-sm text-gray-600';
                    
                    infoContainer.appendChild(timeMarginSpan);
                    infoContainer.appendChild(rating);
                }

				a.appendChild(infoContainer);
				
				getTravelTime({ lat: 35.702332, lng: 139.700003 }, result.geometry.location, (duration) => {
					if (duration) {
					    result.travelTime = duration;
					    a.setAttribute('data-travel-time', `${duration}`);
					
					    const travelTimeDiv = document.createElement('div');
					    travelTimeDiv.className = 'text-sm mt-2 text-gray-600';
					
					    const walkingIcon = document.createElement('i');
					    walkingIcon.className = 'fas fa-walking ml-0.5 mr-1.5';
					    travelTimeDiv.appendChild(walkingIcon);
					
					    travelTimeDiv.appendChild(document.createTextNode(`${duration}`));
					    
					    infoContainer.appendChild(travelTimeDiv);
					}
	
	                travelTimeCounter++;
            	});

                ul.appendChild(a);
            });
        }
    });
}

function getTravelTime(start, end, callback) {
    const request = {
        origin: start,
        destination: end,
        travelMode: 'WALKING'
    };

    directionsService.route(request, (result, status) => {
        if (status == 'OK') {
            const duration = result.routes[0].legs[0].duration.text;
            callback(duration);
        } else {
            callback(null);
        }
    });
}

function fetchRatingsAndCounts() {
  fetch('api/ratings')
    .then(response => response.json())
    .then(data => {
      averageRatings = data.averageRatings;
      reviewCounts = data.reviewCounts;
      ratingsAndCountsFetched = true;
    })
    .catch(error => {
      console.error('Error fetching ratings and counts:', error);
      ratingsAndCountsFetched = false;
    });
}

function createStars(rating) {
    let stars = '';
    for (let i = 0; i < 5; i++) {
        if (i < Math.floor(rating)) {
            stars += '<i class="fas fa-star text-amber-400"></i>';
        } else {
            stars += '<i class="far fa-star text-amber-400"></i>';
        }
    }
    return stars;
}

function sortRestaurants() {
    let sortBy = document.getElementById('sort').value;
    let restaurantList = document.getElementById('restaurant-list');
    let restaurants = Array.from(restaurantList.querySelectorAll('.restaurant-card'));

    if (sortBy === 'rating') {
        restaurants.sort((a, b) => parseFloat(b.getAttribute('data-rating')) - parseFloat(a.getAttribute('data-rating')));
    }

    restaurantList.innerHTML = '';
    restaurants.forEach(restaurant => restaurantList.appendChild(restaurant));
}

window.onload = function() {
    initMap();
    fetchRatingsAndCounts();
};