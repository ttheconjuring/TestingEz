let cropper;
const avatarInput = document.getElementById('avatarInput');
const image = document.getElementById('image');
const cropButton = document.getElementById('cropButton');

avatarInput.addEventListener('change', function (event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            image.src = e.target.result;
            image.style.display = 'block';

            // Destroy any previous cropper instance
            if (cropper) {
                cropper.destroy();
            }

            // Initialize Cropper.js
            cropper = new Cropper(image, {
                aspectRatio: 1,
                viewMode: 1,
                autoCropArea: 0.5,
                movable: true,
                cropBoxResizable: true,
                cropBoxMovable: true,
                ready: function () {
                    cropButton.disabled = false;
                }
            });
        }
        reader.readAsDataURL(file);
    }
});

cropButton.addEventListener('click', function () {
    cropButton.disabled = true;
    if (cropper) {
        const canvas = cropper.getCroppedCanvas({
            width: 300,
            height: 300
        });

        canvas.toBlob(function (blob) {
            const formData = new FormData();
            formData.append('avatar', blob, 'avatar.jpg');

            // Send the form data to the server via AJAX
            fetch('/upload-avatar', {
                method: 'POST',
                body: formData
            })
                .then(response => {
                        if (response.ok) {
                            window.location.href = 'http://localhost:8080/user/profile';
                        } else {
                            alert('Sorry, but something went wrong!');
                        }
                    }
                )
                .catch(error => console.error('Error uploading avatar', error));
        }, 'image/jpeg');
    }
});