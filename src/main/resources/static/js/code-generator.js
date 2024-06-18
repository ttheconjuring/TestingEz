function generateRandomCharFromSet(charSet) {
    const randomIndex = Math.floor(Math.random() * charSet.length);
    return charSet[randomIndex];
}

function generateCode() {
    const letters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
    const numbers = '0123456789';
    const specialSymbols = '!@#$%^&*';

    let code = '';
    code += generateRandomCharFromSet(letters);
    code += generateRandomCharFromSet(letters);
    code += generateRandomCharFromSet(numbers);
    code += generateRandomCharFromSet(numbers);
    code += generateRandomCharFromSet(specialSymbols);
    code += generateRandomCharFromSet(specialSymbols);

    // Shuffle the code
    code = code.split('').sort(() => 0.5 - Math.random()).join('');

    document.getElementById('code').value = code;
}