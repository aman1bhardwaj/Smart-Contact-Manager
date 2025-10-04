Created a Home.html file just for testing purposes. I didn't write the entire file—instead, I used the VS Code shortcut "html:5".
This shortcut generates the basic HTML structure, which you can then modify as needed.

- Installed npm to use Tailwind CSS
- Command to configure Tailwind: `npm install -D tailwindcss; npx tailwindcss init`

The above command will create a tailwind.config.js file. Note that this command works for Tailwind CSS version 3 or earlier.

Next, update the content of tailwind.config.js to specify where changes should be applied and what content Tailwind needs to access.

After completing the above steps:

1. Create a folder named `css` inside `src/main/resources/static/` if it doesn't already exist.
2. Inside this `css` folder, create a file named `input.css` and add the required Tailwind directives:
   ```css
   @tailwind base;
   @tailwind components;
   @tailwind utilities;
   ```

Once you've created the folder and file, run this command in the terminal to generate the `output.css` file:
    npx tailwindcss -i src/main/resources/static/css/input.css -o src/main/resources/static/css/output.css --watch

This command will watch for changes and automatically update the output file.

To use Tailwind CSS in any HTML file, include the generated output.css file:
    <link rel="stylesheet" data-th-href="@{./css/output.css}" />

If you want to include Flowbite in your HTML file:

1. Add this link in the head section of your HTML file:
        <link href="<https://cdn.jsdelivr.net/npm/flowbite@3.1.2/dist/flowbite.min.css>" rel="stylesheet" />

2. Add this script just before the closing body tag:
         <script src="<https://cdn.jsdelivr.net/npm/flowbite@3.1.2/dist/flowbite.min.js>"></script>

# Fragments in ThymeLeaf:

## Creation of Fragment:

Below are the basically base.html file where fragment is created and which can be used in other html files on the basis of requirement. It is just an example of how to create a fragment.

```html
<div class="parent-fragment" th:fragment="parent(x,y)">
    <h1> this is a parent fragment </h1>
    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quidem perspiciatis sed recusandae maxime tempora, expedita exercitationem! Vel amet eveniet 
        voluptatibus a quo explicabo suscipit, ipsa pariatur officia accusantium fugit sunt?
    </p>
    <h1>Value of x is  <span th:text="${x}"></span></h1>
    <h1>Value of y is  <span th:text="${y}"></span></h1>
</div>

<div class ="content-parent-fragment" th:fragment="contest(content)">
    <h1>This is content from base.html</h1>
    <p>This is a paragraph from base.html file</p>
    <div class ="content-fragment" th:replace="${content}"></div>
</div>
```

## Insert and Replace fragments:

Below are the mail file where fragment can be used using insert and replace . Both the example present below how to use the fragments.

```html
<!-- using fragment with dynamic content -->
    <div class="service-class" th:replace="~{base :: parent('services one','services two')}"></div>

    <!-- using fragment to replace whole structure and pass it to content -->
    <div class="test-content" th:replace="~{base :: contest(~{::p})}">
        <div th:fragment="p">
            <h1>This is from services.html file</h1>
            <p>This is a paragraph from services.html file</p>
        </div>
    </div>
```