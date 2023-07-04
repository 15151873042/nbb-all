function encryption(pass) {
    var key = "label@wonder.com";
    key = CryptoJS.enc.Latin1.parse(key)
    var iv = key
    var encrypted = CryptoJS.AES.encrypt(pass, key, {
        iv: iv,
        mode: CryptoJS.mode.CBC,
        padding: CryptoJS.pad.ZeroPadding
    })
    return encrypted.toString()
}