package gr.aueb.cf;

// Importing the Course and Teacher model classes
import gr.aueb.cf.model.Course;
import gr.aueb.cf.model.Teacher;

// Importing JPA classes for entity management
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class App {

    // Create a single EntityManagerFactory for the persistence unit "school8PU"
    private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("school8PU");
    // Create an EntityManager instance from the factory
    private final static EntityManager em = emf.createEntityManager();

    public static void main(String[] args) {

        // --- Example code for creating a teacher and course (currently commented out) ---
        // Teacher teacher = new Teacher(null, "John", "Doe");
        // Course course = new Course(null, "Java Programming");
        // teacher.addCourse(course);

        // Start a new transaction
        em.getTransaction().begin();

        // Find an existing Teacher entity with primary key 1L
        Teacher teacher = em.find(Teacher.class, 1L);
        // Update the teacher's first name to "Jane"
        teacher.setFirstname("Jane");
        // Merge the updated teacher entity back into the persistence context
        em.merge(teacher);

        // Find the Course entity with primary key 1L (change as needed)
        Course course = em.find(Course.class, 1L);
        if (course != null) {
            em.remove(course); // Remove the course from the database
        }

        // Example query to select all Teacher entities (currently not executed)
        String sql = "Select t From Teacher t";
        List<Teacher> teachers = em.createQuery(sql, Teacher.class).getResultList();
        teachers.forEach(System.out::println);

        // Example query to select all Course entities taught by a specific teacher
        String sql2 = "Select c From Course c WHERE c.teacher.lastname = :lastname";
        TypedQuery<Course> query = em.createQuery(sql2, Course.class);
        List<Course> courses = query.setParameter("lastname", "Ανδρούτσος")
                .getResultList();
        courses.forEach(System.out::println);


        // --- Example code for persisting new entities (currently commented out) ---
        // em.persist(teacher);
        // em.persist(course);

        // Commit the transaction to save changes to the database
        em.getTransaction().commit();

        // Close the EntityManager to release resources
        em.close();
        // Close the EntityManagerFactory to release resources
        emf.close();
    }
}