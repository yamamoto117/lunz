let map;
let service;
let directionsService;
let currentPhotoIndex = 0;

function initDetail() {
    const initialLatLng = { lat: 35.702332, lng: 139.700003 }; //ジードライブの座標
    map = new google.maps.Map(document.getElementById('map'), { center: initialLatLng, zoom: 18 });
    service = new google.maps.places.PlacesService(map);
    directionsService = new google.maps.DirectionsService();

    const blueIcon = 'https://maps.google.com/mapfiles/ms/icons/blue-dot.png';

    const initialMarker = new google.maps.Marker({
        position: initialLatLng,
        map: map,
        title: 'ジードライブ',
        icon: blueIcon
    });

    if (placeId) {
        const request = {
            placeId: placeId,
            fields: ['name', 'formatted_phone_number', 'website', 'geometry', 'formatted_address', 'opening_hours', 'photos']
        };

        service.getDetails(request, displayDetails);
    }

    document.querySelector('.logo').addEventListener('click', resetSearchState);
}

function displayDetails(place, status) {
    if (status === google.maps.places.PlacesServiceStatus.OK) {
        document.getElementById('name').textContent = place.name;

        document.getElementById('address').textContent = place.formatted_address.replace('日本、', '');

        if (place.opening_hours && place.opening_hours.weekday_text) {
            document.getElementById('hours').innerText = place.opening_hours.weekday_text.join('\n');
        } else {
            document.getElementById('hours').innerText = "情報はありません";
        }

        if (place.opening_hours && place.opening_hours.weekday_text) {
            document.getElementById('phone').textContent = place.formatted_phone_number;
        } else {
            document.getElementById('phone').textContent = "情報はありません";
        }

        const websiteLink = document.getElementById('website');
        if (place.website) {
            websiteLink.href = place.website;
            websiteLink.textContent = place.website;
            websiteLink.classList.add('text-blue-500', 'underline', 'hover:no-underline');
            websiteLink.style.pointerEvents = "";
        } else {
        websiteLink.removeAttribute('href');
        websiteLink.textContent = "情報はありません";
        websiteLink.classList.remove('text-blue-500', 'underline', 'hover:no-underline');
        websiteLink.style.pointerEvents = "none";
        }

        if (place.geometry && place.geometry.location) {
            map.setCenter(place.geometry.location);

            const marker = new google.maps.Marker({
                position: place.geometry.location,
                map: map,
                title: place.name
            });
        }

        if (place.photos && place.photos.length > 0) {
            const photoContainer = document.getElementById('photo-container');
            place.photos.forEach(photo => {
            const photoUrl = photo.getUrl({ 'maxWidth': 800, 'maxHeight': 800 });
            const imgElement = document.createElement('img');
            imgElement.src = photoUrl;
            imgElement.alt = "お店の写真";
            imgElement.className = "w-3/6 h-3/6 object-cover rounded";
            photoContainer.appendChild(imgElement);
            });
            showPhoto(0);
        }

    } else {
        console.error('Error fetching place details:', status);
    }

    const initialLatLng = { lat: 35.702332, lng: 139.700003 }; //ジードライブの座標

    getTravelTime(initialLatLng, place.geometry.location, (duration) => {
    if (duration) {
        const travelTimeContainer = document.getElementById('travel-time');
        travelTimeContainer.textContent = `${duration}`;
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

function showPhoto(index) {
    const photos = document.querySelectorAll('#photo-container img');
    if (index >= 0 && index < photos.length) {
        photos[currentPhotoIndex].style.display = 'none';
        photos[index].style.display = 'block';
        currentPhotoIndex = index;
    }
}

function showPreviousPhoto() {
    showPhoto(currentPhotoIndex - 1);
}

function showNextPhoto() {
    showPhoto(currentPhotoIndex + 1);
}

function resetSearchState() {
    sessionStorage.removeItem('searchState');
}

