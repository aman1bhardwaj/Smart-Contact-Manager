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
  <h1>this is a parent fragment</h1>
  <p>
    Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quidem
    perspiciatis sed recusandae maxime tempora, expedita exercitationem! Vel
    amet eveniet voluptatibus a quo explicabo suscipit, ipsa pariatur officia
    accusantium fugit sunt?
  </p>
  <h1>Value of x is <span th:text="${x}"></span></h1>
  <h1>Value of y is <span th:text="${y}"></span></h1>
</div>

<div class="content-parent-fragment" th:fragment="contest(content)">
  <h1>This is content from base.html</h1>
  <p>This is a paragraph from base.html file</p>
  <div class="content-fragment" th:replace="${content}"></div>
</div>
```

## Insert and Replace fragments:

Below are the mail file where fragment can be used using insert and replace . Both the example present below how to use the fragments.

```html
<!-- using fragment with dynamic content -->
<div
  class="service-class"
  th:replace="~{base :: parent('services one','services two')}"
></div>

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
<div
  class="new-tag"
  th:replace="${isLogin} ? ~{base ::contest(~{::section})} : ~{base :: parent('Not logged in','Never logged in ')}"
>
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

                                        OR

    Below also can be used to map the values from the form to the DTO.

        User user = new User();
        user.setName(userform.getName());
        user.setEmail(userform.getEmail());
        user.setPassword(userform.getPassword());
        user.setAbout(userform.getAbout());
        user.setPhoneNumber(userform.getPhoneNumber());
        user.setProfilePic(profile_pic);

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

# Sending message from the controller to the Form

    we can use HttpSession object to send the message /project to the form using the set attribute method present in Http Session.

        Message msg = new Message();
        msg.setType(MessageType.green);
        msg.setContent("Registered Successfully");

        session.setAttribute("message", msg);

# Receiving message at the Sign Up form from the controller

    Since , object was passed from the controller , we will check if sessions contain any object or not and on teh basis of that we will take the object and use its properties in mapping details on teh form like below :

        <div
        th:if="${session.message}"
        th:object="${session.message}"

    Using the property :
        <span th:text="*{content}"></span>

# Dynamic message display on the basis of controller

    In the previous explanation, we observed that the message was passed from the session to the form. However, we should not hardcode the message type in the template, as it may vary depending on the context (e.g., success(green) or failure(red)).

    To implement a dynamic message display system — where the controller sends the message containig content (such as "success", "error", "info") and its type(green ,red , yellow) — we need to structure message.html to accept and respond to this type dynamically.

            <div
        th:if="${session.message}"
        th:object="${session.message}"
        th:fragment="messageBox"
        th:classappend="*{'text-sm text-' + type + '-800 border border-' + type + '-300 rounded-lg bg-' + type + '-50 dark:bg-gray-800 dark:text-' + type + '-400 dark:border-' + type + '-800'}"
        class="flex items-center p-4 mb-4"
        role="alert"

# Points to remeber while linking object from form to controller and back to form :

    # Attribute name rules (Spring MVC + Thymeleaf) :
        Thymeleaf expressions are case-sensitive. th:object="${userForm}" looks for a model attribute exactly named userForm.

        Spring’s default name for an @ModelAttribute is the simple class name with its first letter lowercased. For class Userform or UserForm the default becomes userform (not userForm) unless you explicitly name it.

        To avoid ambiguity, always pick one name and use it consistently in controller and template.

    # How to make names match (recommended patterns)

        Explicitly name the model attribute in the controller GET:

            model.addAttribute("userForm", new Userform());

            Template: <form th:object="${userForm}" ...>

        Explicitly name the @ModelAttribute in the POST handler so Spring exposes the object and BindingResult under the same name:

            @PostMapping("/do-register") public String submit(@Valid @ModelAttribute("userForm") Userform userForm, BindingResult br, Model model) { ... }

        Ordering requirement:

            BindingResult parameter must come immediately after the @ModelAttribute parameter in the method signature:

                correct: (@ModelAttribute("userForm") Userform userForm, BindingResult br, ...)

                incorrect: (BindingResult br, @ModelAttribute("userForm") Userform userForm, ...)

            If you return the view on validation errors, you do not need to re-add the attribute if you used @ModelAttribute("userForm") correctly because Spring will expose both the target object and its BindingResult automatically. If you did not name the attribute explicitly, add it to the model before returning.

# Spring Security :

    We want some page can't be accessed directly typing from the URL and can be accesses only after correct authentications/
     For exx : user.dashboard , etc.

     SO beasue of this reason we are sunig spring security.

# Spring Security Default User Configuration

In this project, I’ve configured the default Spring Security user using application.yml only. Here's the setup and some important notes for future reference:

    ✅ Current Configuration (application.yml)
            yaml
            spring:
            security:
                user:
                name: aman
                password: yourPassword

        This YAML format offers better readability and structure, especially when dealing with nested configurations.

    🧩 Alternative: Using application.properties

        Although not used in this project, the same configuration can be written in application.properties like this:

            properties
            spring.security.user.name=aman
            spring.security.user.password=yourPassword

        This is equally valid and often preferred in simpler setups or legacy projects.

    ⚙️ File Loading Order

        If both files are present:
            application.yml is loaded first
            application.properties is loaded after

        If both define the same property, the last one loaded wins — meaning application.properties will override application.yml

    🧠 Why I Chose YAML
            Cleaner structure for nested configs
            Easier to maintain and extend
            Aligns with modern Spring Boot practices

# How Spring security works

    now there are multiple services available. Lets say , if we say we have user present in memory only , tehn we have InMemoryUserDetailsService

    --------------


    🔍 How Spring Security Authenticates Users
        Spring Security uses the UserDetailsService interface to load user data during login.

        The core method is:
            UserDetails loadUserByUsername(String username)

        This method is called automatically when a login request is made.

        Once the user is loaded, Spring Security compares:

        The password stored in the returned UserDetails

        The password provided during login

        If they match, authentication succeeds; otherwise, it fails.

    there are multiple services available out of which one is InMemoryUserDetailsService.
    In-Memory User Setup (For Testing Only)

            @Bean
            public UserDetailsService userDetailService() {
                UserDetails userDetails1 = User
                    .withDefaultPasswordEncoder()
                    .username("admin123")
                    .password("admin123")
                    .build(); // Uses deprecated encoder for quick testing

                UserDetails userDetails2 = User
                    .withUsername("userr")
                    .password("userr123")
                    .build(); // Will fail login due to missing encoder

                return new InMemoryUserDetailsManager(userDetails1, userDetails2);
            }

    This method creates two users manually in memory.

    It uses withDefaultPasswordEncoder() for simplicity — not recommended for production.

    userDetails2 will fail login because no password encoder is applied.

    This setup is purely for testing and understanding how Spring Security works.

# Why UserDetails needs to be impemented?

    Why This Is Needed
        Spring Security does not know your custom User entity. To authenticate users from your database, you must adapt your entity to Spring’s expected format using the UserDetails interface.
        This tells Spring:
        ✅ What the username is (e.g., email or username)
        ✅ What the password is
        ✅ What roles/authorities the user has (e.g., ROLE_USER, ROLE_ADMIN)
        ✅ Whether the account is enabled, locked, expired, etc.

    🔄 How It Fits Into the Flow

        A[User logs in] --> B[Spring Security]
        B --> C[UserDetailsService.loadUserByUsername()]
        C --> D[Returns UserDetails object]
        D --> E[Spring authenticates using UserDetails]

==> Integration Steps (Minimal) 1. Your User Entity
You already have a User class with fields like email, password, enabled, etc.

        2. Implement UserDetails
            Either:

            Create a wrapper class like MyUserDetails implements UserDetails, or

            Implement UserDetails directly in your User entity (you’ve done this ✅)

            Implement these methods:

            java
            @Override
            public String getUsername() { return this.email; }

            @Override
            public String getPassword() { return this.password; }

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of(new SimpleGrantedAuthority("ROLE_USER"));
            }

            @Override public boolean isAccountNonExpired() { return true; }
            @Override public boolean isAccountNonLocked() { return true; }
            @Override public boolean isCredentialsNonExpired() { return true; }
            @Override public boolean isEnabled() { return this.enabled; }
            You can later replace "ROLE_USER" with dynamic roles from DB if needed.

        3. Implement UserDetailsService

            @Service
            public class MyUserDetailsService implements UserDetailsService {
                @Autowired
                private UserRepository userRepository;

                @Override
                public UserDetails loadUserByUsername(String username) {
                    User user = userRepository.findByEmail(username);
                    return user; // since User implements UserDetails
                }
            }

    ✅ Summary
    Component	:            Responsibility
    UserDetails	:            Exposes user credentials and roles to Spring
    UserDetailsService	:    Loads user from DB and returns a UserDetails
    SecurityConfig	:        Wires the UserDetailsService into Spring Security

    note :
        If you want to avoid a wrapper class, implement UserDetails directly in your entity (as you’ve done).
        For multiple roles, consider adding a List<Role> and map it to GrantedAuthority.
        Use email as username if that’s your login field.

# Spring Security URL Access Configuration — Notes

    Purpose
        This configuration defines which URLs in the application are publicly accessible and which require authentication. It’s part of the SecurityFilterChain setup using the fluent API introduced in Spring Security 5+.

    Code Breakdown

            httpSecurity.authorizeHttpRequests(authorize -> {
                // authorize.requestMatchers("/home", "/register", "/services").permitAll();
                authorize.requestMatchers("/user/**").authenticated();
                authorize.anyRequest().permitAll();
            });

        Explanation:
            authorizeHttpRequests(...): Starts the authorization configuration block.
            requestMatchers(...): Specifies URL patterns to match.
            .authenticated(): Requires the user to be logged in to access the matched URLs.
            .permitAll(): Allows access to the matched URLs without authentication.

    What This Configuration Does
        URL Pattern	-> Access Type	-> Description
        /user/**	- > 🔒 Authenticated	- > Only logged-in users can access any endpoint under /user/
        All other requests	-> 🔓 Public	-> Any other URL is accessible without login

    Note: The line for /home, /register, and /services is commented out. If uncommented, those specific URLs would be explicitly marked as public.

    Best Practices
        Always define public endpoints first to avoid accidental overrides.
        Use requestMatchers("/admin/**").hasRole("ADMIN") for role-based access.
        Consider grouping public URLs into a constant list for maintainability.

# Spring Security formLogin Configuration — Notes

    Purpose :
        This configuration customizes the behavior of Spring Security’s form-based login. It defines:

            The login page URL
            The endpoint that processes login submissions
            Where to redirect on success or failure
            What request parameters to treat as username and password

       formLogin.loginPage("/login")
       .loginProcessingUrl("/authenticate")
       .successForwardUrl("/user/dashboard")
        .failureForwardUrl("/login?error=true")
        .usernameParameter("email")
        .passwordParameter("password");

        loginPage("/loginUser"): Specifies a custom login page. Instead of using Spring Security’s default /login, the app will show the login form at /loginUser.
        loginProcessingUrl("/authenticate"): This is the endpoint that Spring Security listens to for login form submissions. The form should POST to /authenticate.
        successForwardUrl("/user/dashboard"): After a successful login, the user will be forwarded to /user/dashboard.
        failureForwardUrl("/login?error=true"): If login fails, the user will be forwarded to /login?error=true, which can be used to show an error message.
        usernameParameter("email"): Spring Security will treat the email field in the login form as the username.
        passwordParameter("password"): Spring Security will treat the password field in the login form as the password.

    Important Notes:

        The login form must contain input fields named email and password.
        The login page (/loginUser) must be publicly accessible, otherwise users won’t be able to reach it.
        This configuration uses forwarding, not redirecting. If you prefer redirects, you can use .defaultSuccessUrl(...) and .failureUrl(...) instead.

# What Is CSRF ?
    CSRF (Cross-Site Request Forgery) in Spring Security is a built-in protection mechanism that prevents malicious websites from performing unauthorized actions on behalf of authenticated users. It works by requiring a unique token to be present in every state-changing request, ensuring the request is intentional and originates from your own application.


    Imagine you're logged into your bank account in one browser tab. In another tab, you visit a shady website that secretly tries to transfer money from your account by submitting a form to your bank’s server. Since you're already logged in, your browser includes session cookies — and without CSRF protection, the bank might accept that malicious request.

    CSRF is like a secret handshake between your browser and server — unless the handshake is correct, the server rejects the request.

    How Spring Security Handles CSRF
        Spring Security enables CSRF protection by default for any HTTP method that modifies data:
            POST
            PUT
            PATCH
            DELETE
        It does not apply to safe methods like GET, HEAD, OPTIONS, or TRACE.

    CSRF Token Lifecycle
        Token Generation When a user accesses the app, Spring generates a unique CSRF token and stores it in the session.

        Token Injection The token must be included in every form or AJAX request that modifies data. For HTML forms, Spring automatically adds it via Thymeleaf:

            html
            <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />

        Token Validation When the request reaches the server, Spring checks if the token matches the one stored in the session. If it’s missing or invalid, the request is rejected with a 403 Forbidden error.

    Customizing CSRF Behavior
        You can configure CSRF in your SecurityFilterChain:

            java
            http.csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            );

        This stores the token in a cookie, which is useful for JavaScript-based clients (React, Angular) that need to read and send the token manually.

    When to Disable CSRF
            You can safely disable CSRF if:
            You're building a stateless REST API
            You're using JWT tokens for authentication
            You don’t rely on cookies or sessions

            java
            http.csrf().disable();

        But never disable CSRF in a browser-based app with session login, unless you fully understand the risks.

    Advanced Insights:
        Spring Security uses CsrfFilter in its filter chain to intercept and validate CSRF tokens.
        You can implement a custom CsrfTokenRepository to store tokens in headers, cookies, or elsewhere.
        CSRF protection is tightly integrated with Spring’s session management — if sessions are disabled, CSRF won’t work unless manually configured.

    Summary :
        CSRF prevents unauthorized actions from other sites using your session
        Spring Security enables CSRF protection by default for unsafe methods
        Tokens are generated, injected, and validated automatically 
        Use CookieCsrfTokenRepository for frontend frameworks
        Disable CSRF only for stateless APIs or token-based auth.


# OAuth2 : Login with google or github :-

    Add the following dependency in pom.xml:

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-oauth2-client</artifactId>
        </dependency>

    Set up Google Cloud Console for OAuth:

        Go to APIs & Services → OAuth consent screen.
        Configure the App name, support email, scopes, and other required fields.
        Create an OAuth Client ID:
        
            Application type: Web application
            Add Authorized redirect URIs, e.g.:
            http://localhost:8080/login/oauth2/code/google

        Copy the Client ID and Client Secret.

    Configure OAuth in your Spring Security filter chain:

        Define the login page.
        Configure an AuthenticationSuccessHandler to handle successful authentication.

    Customize AuthenticationSuccessHandler:

        Implement the AuthenticationSuccessHandler interface.
        Override its methods to:

            Retrieve user details from the authentication object.
            Save user details to the database.
            Redirect the user to a specific URL after successful authentication.

# 🧩 What is a Principal in Spring Boot?

    In Spring Boot / Spring Security, the term Principal represents the currently authenticated user — i.e., the person (or system) who is logged in and making the current request.

    It comes from Java’s java.security.Principal interface, which defines one method:
        public String getName();

    That’s it — it’s a very simple representation of the identity of the user.

    🧠 Why do we use Principal in Spring Boot?

        When a user logs in (via form login, JWT token, OAuth2, etc.), Spring Security stores authentication details in a SecurityContext.
        From that context, you can get the logged-in user in multiple ways — and Principal is one of the simplest.
        It’s mainly used to:

            Identify the logged-in user in a controller or service.
            Access the username or user details.
            Implement user-specific logic (for example, “show only that user’s data”).

        🧱 Example: Using Principal in a Controller
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.RestController;
        import java.security.Principal;

        @RestController
        public class UserController {

            @GetMapping("/user")
            public String getUser(Principal principal) {
                return "Hello, " + principal.getName();
            }
        }


    ✅ What happens:

    When a logged-in user sends a request to /user,
    Spring Security automatically injects the Principal object,
    principal.getName() gives you the username (from the authentication object).

    ⚙️ Alternatives to Principal

        Sometimes you’ll see these instead:

        Type	                                            Description
        Authentication	                                    Spring Security’s richer object containing roles, credentials, and details.
        @AuthenticationPrincipal UserDetails user	        Directly injects your custom UserDetails object.

            Example:

            @GetMapping("/profile")
            public String getProfile(@AuthenticationPrincipal UserDetails user) {
                return "Welcome, " + user.getUsername();
            }


