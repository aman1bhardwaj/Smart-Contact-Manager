console.log("Script loaded for theme changed button")

let currentTheme = getTheme();

document.addEventListener("DOMContentLoaded", () => {
    changeTheme();
});

function changeTheme() {

        //set to Web Page
    changePageTheme(currentTheme, "");

    //set the listener to change theme button
    const changeThemeButton = document.querySelector('#theme_change_button');
 
    changeThemeButton.addEventListener("click", (event) => {
           let oldTheme = currentTheme;
        console.log("Theme change button clicked");
        if (currentTheme === 'Dark') {
            //theme to Light
            currentTheme = "Light";
        } else {
            //theme to Dark
            currentTheme = "Dark";
        }
        console.log(currentTheme);
        changePageTheme(currentTheme, oldTheme);
    });
}

function setTheme(theme) {
    localStorage.setItem('theme', theme);
}

function getTheme() {
    let theme = localStorage.getItem('theme');
    return theme ? theme :"Light";
}

function changePageTheme(theme, oldTheme) {

    //Theme update in the local Storage 
    setTheme(theme);

    //Remove the old theme and add the new theme to the web page
    if(oldTheme){
    document.querySelector("html").classList.remove(oldTheme);
    }

    document.querySelector("html").classList.add(theme);

    //Change the button text
    document.querySelector('#theme_change_button').querySelector("span").textContent = theme == "Light" ? "Dark" : "Light";
}