# Smart-Contact-Manager

This project will store and manages the contacts.

# Below are notes mentioned only for knowledge purpose

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
