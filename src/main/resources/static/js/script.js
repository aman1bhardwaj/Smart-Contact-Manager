console.log("Script loaded for theme changed button")

let currentTheme = getTheme();

document.addEventListener("DOMContentLoaded", () => {
    changeTheme();
    console.log("Document fully loaded");
}
);




function changeTheme() {

    //set the listener to change theme button
    const changeThemeButton = document.querySelector('#theme_change_button');

    //set to Web Page
    changePageTheme(currentTheme, currentTheme);

    const oldTheme = currentTheme;

    changeThemeButton.addEventListener("click", (event) => {
        console.log("Theme change button clicked");
        if (currentTheme === 'Dark') {
            //theme to Light
            currentTheme = "Light";
        } else {
            //theme to Dark
            currentTheme = "Dark";
        }
        changePageTheme(currentTheme, oldTheme);
    });
}

function setTheme(theme) {
    localStorage.setItem('theme', theme);
}

function getTheme() {
    let theme = localStorage.getItem('theme');
    if (theme == null) return 'Light';
    else return theme;
}

function changePageTheme(theme, oldTheme) {

    //Theme update in the local Storage 
    setTheme(theme);

    //Remove the old theme and add the new theme to the web page
    document.querySelector("html").classList.remove(oldTheme);

    document.querySelector("html").classList.add(theme);

    console.log("Theme changed to " + theme);

    document.querySelector('#theme_change_button').querySelector("span").textContent = theme == "Light" ? "Dark" : "Light";

}