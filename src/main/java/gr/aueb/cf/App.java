package gr.aueb.cf;

// Importing the Course and Teacher model classes
import gr.aueb.cf.model.Course;
import gr.aueb.cf.model.Teacher;

// Importing JPA classes for entity management
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Objects;

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
        //Teacher teacher = em.find(Teacher.class, 1L);
        // Update the teacher's first name to "Jane"
        //teacher.setFirstname("Θανάσης");
        // Merge the updated teacher entity back into the persistence context
        //em.merge(teacher);

        // Find the Course entity with primary key 1L (change as needed)
//        Course course = em.find(Course.class, 1L);
//        if (course != null) {
//            em.remove(course); // Remove the course from the database
//        }

        // Example query to select all Teacher entities (currently not executed)
//        String sql = "Select t From Teacher t";
//        List<Teacher> teachers = em.createQuery(sql, Teacher.class).getResultList();
//        teachers.forEach(System.out::println);
//
//        // Example query to select all Course entities taught by a specific teacher
//        String sql2 = "Select c From Course c WHERE c.teacher.lastname = :lastname";
//        TypedQuery<Course> query = em.createQuery(sql2, Course.class);
//        List<Course> courses = query.setParameter("lastname", "Ανδρούτσος")
//                .getResultList();
//        courses.forEach(System.out::println);

//        String sql3 = "Select t,c From Teacher t JOIN t.courses c";
//        List<Objects[]> teacherCourses = em.createQuery(sql3, Objects[].class)
//                .getResultList();
//        teacherCourses.forEach(System.out::println);

        // Example query to count the number of courses for each teacher
//        String sql4 = "Select t.lastname, count(c) From Teacher t JOIN t.courses c GROUP BY t";
//        List<Object[]> teacherCourseCounts = em.createQuery(sql4, Object[].class)
//                .getResultList();
//        for (Object[] objectArr : teacherCourseCounts) {
//            System.out.println("Teacher: " + objectArr[0] + ", Course Count: " + objectArr[1]);
//        }

        // Criteria API
        // SELECT cources

//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Course> query = cb.createQuery(Course.class); // what returns
//        Root<Course> root = query.from(Course.class);               // Root entity
//        // default is SELECT c FROM Course c.
//        //query.select(root);                                        // Select all fields
//
//        List<Course> courses = em.createQuery(query).getResultList(); // Execute the query
//        courses.forEach(System.out::println);                         // Print the results



        // Select all teachers with last name "Ανδρούτσος" using Criteria API.

//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Teacher> query = cb.createQuery(Teacher.class); // What returns
//        Root<Teacher> teacherRoot = query.from(Teacher.class);               // Root entity
//        //query.select(teacherRoot).where(cb.equal(teacherRoot.get("lastname"), "Ανδρούτσος")); // SQL Injection!!!
//
//        List<Teacher> teachers =em.createQuery(query).getResultList(); // Execute the query
//        teachers.forEach(System.out::println); // Print the results

        //SQL Injection Safe.
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Teacher> query = cb.createQuery(Teacher.class); // What returns
        Root<Teacher> teacherRoot = query.from(Teacher.class);
        // Root entity
        ParameterExpression<String> lastnameParam = cb.parameter(String.class); // Create a parameter for the lastname
        query.select(teacherRoot).where(cb.equal(teacherRoot.get("lastname"), lastnameParam)); // SQL Injection!!!

        List<Teacher> teachers =em.createQuery(query).setParameter(lastnameParam, "Ανδρούτσος").getResultList(); // Execute the query
        teachers.forEach(System.out::println); // Print the results




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