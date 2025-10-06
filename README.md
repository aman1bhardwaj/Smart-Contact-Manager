# Smart-Contact-Manager
This project will store and manages the contacts.

# Below are notes mentioned only for knowledge purpose 
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

NameSpace ussed in thymeleaf : <html xmlns:th="http://www.thymeleaf.org">

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

## Expressions in fragments:

To use fragments based on conditions or expressions. The example below shows how this can be achieved. The attribute ${isLogin} comes from the controller, and based on its value, we evaluate which fragment to call.

```html
<div class="new-tag" th:replace="${isLogin} ? ~{base ::contest(~{::section})} : ~{base :: parent('Not logged in','Never logged in ')}">
        <section>
            <h1>USER IS LOGGED IN</h1>
            <p>This is a paragraph from about.html file where user is logged in.</p>
            <button class="bg-blue-500 text-white p-2 rounded">Click Me</button>
        </section>
    </div>
```



# Properties related to Hibernate / JPA configuration

```
spring.jpa.show-sql=true  :To show in the logs query being used by hibernate to create t he table or doing any operation to the DB.
# For creating schemas and tables automatically
spring.jpa.hibernate.ddl-auto=update
# For SQL formatting
spring.jpa.properties.hibernate.format_sql=true
```

# Lombok Features:

Lombok reduces boilerplate code in entity classes. The annotations below eliminate the need to manually write setters, getters, constructors, parameterized constructors, and toString methods. Simply using these annotations is sufficient.

```
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
```

# Project Relations explaination:

    User --onetoMany--Contact --OnetOne-- Address
         --oneToMany--SocialLink

# Explanation of relations :

@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true) :

    This line is used in your User entity to define a one-to-many relationship with Contact. Here's what each part means:

    @OneToMany: This sets up a one-to-many relationship, meaning one User can have multiple Contacts.

    mappedBy = "user": This tells JPA that the Contact entity owns the relationship. In other words, the Contact class has a field named user annotated with @ManyToOne, and that’s where the foreign key lives. This avoids creating a separate join table.

    cascade = CascadeType.ALL: Any operation performed on a User (like save, update, delete) will automatically be applied to its associated Contacts. So if you delete a User, all their Contacts will be deleted too.

    fetch = FetchType.LAZY: The list of Contacts won’t be loaded from the database until you explicitly access it. This improves performance by avoiding unnecessary data loading.

    fetch = FetchType.EAGER:With EAGER, the associated contacts list is loaded immediately when the User entity is fetched from the database.
    This means even if you just query for a User, Hibernate will also fetch all related Contacts in the same query or via a join.

    orphanRemoval = true: If you remove a Contact from the User's contacts list (e.g., user.getContacts().remove(contact)), that Contact will be deleted from the database automatically — assuming it's no longer referenced elsewhere.


# Once Sign UP form is Created:

Once the Signup form is created, the goal is to:

    Capture user input from the form.
    Send the data to the backend.
    Validate the input.
    Persist the data to the database.
    Handle exceptions gracefully.
    Redirect to the registration page upon successful save.

To facilitate data transfer between the frontend form and backend logic, we need an intermediate Java class (often called a DTO or form-binding object) that mirrors the fields of the Signup form. This class acts as a bridge between the UI and the persistence layer, enabling smooth data binding and validation.

# Mapping a DTO (userForm) to an HTML Form Using Thymeleaf
    1️⃣ Sending an Empty DTO to the Form
    When the /register endpoint is invoked, an empty instance of the userForm DTO is passed to the HTML view. This object will be used to bind form fields to DTO properties.

    2️⃣ Binding the DTO to the Form
    To bind the form to the userForm object, use the th:object attribute in your <form> tag:

        html
        <form 
        data-th-action="@{/do-register}" 
        method="post" 
        th:object="${userForm}"
        class="mt-5"/>
        This tells Thymeleaf that all form fields inside this block will be mapped to the userForm DTO.

    3️⃣ Mapping Individual Fields
    Each input field in the form should be linked to a corresponding property in the DTO using the th:field attribute. For example, to bind the name property:

            html
            <input
            type="text"
            id="name"
            th:field="*{name}"
            placeholder="Enter name here"
            required
            />
    This ensures that the value entered in the input field is automatically mapped to the name property of the userForm DTO when the form is submitted.

# Getting Data from Form to Controller
Use @ModelAttribute to bind form data to a controller method.

    Construct a User object using the builder pattern:

        java
        User user = User.builder()
            .name(userform.getName())
            .email(userform.getEmail())
            .password(userform.getPassword())
            .phoneNumber(userform.getPhoneNumber())
            .about(userform.getAbout())
            .profilePic(profile_pic) // default profile pic
            .build();

    Set default values using application.properties:

        properties
        user.default.profile-pic=default.png

    Inject the value in the controller using:

        java
        @Value("${user.default.profile-pic}")
        private String profile_pic;


# Saving Data to the Database
    We created a repository by extending JpaRepository, which provides most of the methods needed to perform database operations. It also allows us to define custom methods based on specific requirements.

        java
        public interface UserRepository extends JpaRepository<User, Long> {
            // Custom query methods if needed
        }

    Next, we created an interface UserService that declares all the required methods without providing their implementations—essentially abstract method definitions.

    Then, we implemented UserService in a class called UserServiceImpl, where we injected the UserRepository and provided concrete implementations for the declared methods.

    Finally, in the controller, since the builder logic and service implementations were already in place, we simply injected UserService and called the saveUser method to persist the user data.




    