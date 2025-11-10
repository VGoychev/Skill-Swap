package com.skillswapplatform.sample;

import com.skillswapplatform.category.model.Category;
import com.skillswapplatform.category.repository.CategoryRepository;
import com.skillswapplatform.offer.model.LocationType;
import com.skillswapplatform.offer.model.Offer;
import com.skillswapplatform.offer.model.OfferStatus;
import com.skillswapplatform.offer.model.OfferType;
import com.skillswapplatform.offer.repository.OfferRepository;
import com.skillswapplatform.skill.model.Skill;
import com.skillswapplatform.skill.repository.SkillRepository;
import com.skillswapplatform.user.model.User;
import com.skillswapplatform.user.model.UserRole;
import com.skillswapplatform.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@Profile("dev")
public class DataSeeder {

    @Bean
    CommandLineRunner seedDatabase(
            UserRepository userRepository,
            CategoryRepository categoryRepository,
            SkillRepository skillRepository,
            OfferRepository offerRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            // Check if data already exists
            if (userRepository.count() > 0) {
                System.out.println("Database already contains data. Skipping seeding.");
                return;
            }

            System.out.println("Seeding database with sample data...");

            // Create Categories
            Category programming = createCategory("Programming", "Software development and coding");
            Category design = createCategory("Design", "Graphic design, UI/UX, and visual arts");
            Category languages = createCategory("Languages", "Foreign language learning");
            Category music = createCategory("Music", "Musical instruments and theory");
            Category cooking = createCategory("Cooking", "Culinary arts and recipes");
            Category fitness = createCategory("Fitness", "Physical training and wellness");

            List<Category> categories = List.of(programming, design, languages, music, cooking, fitness);
            categoryRepository.saveAll(categories);

            // Create Skills
            Skill java = createSkill("Java Programming", programming, "Object-oriented programming in Java");
            Skill python = createSkill("Python", programming, "Python programming for beginners and advanced");
            Skill react = createSkill("React.js", programming, "Modern web development with React");
            Skill webDesign = createSkill("Web Design", design, "Creating beautiful websites");
            Skill spanish = createSkill("Spanish", languages, "Learn Spanish conversation");
            Skill guitar = createSkill("Guitar", music, "Acoustic and electric guitar lessons");
            Skill cooking101 = createSkill("Italian Cooking", cooking, "Traditional Italian cuisine");
            Skill yoga = createSkill("Yoga", fitness, "Hatha and Vinyasa yoga");

            List<Skill> skills = List.of(java, python, react, webDesign, spanish, guitar, cooking101, yoga);
            skillRepository.saveAll(skills);

            // Create Users
            User alice = createUser("alice", "alice@example.com", "Alice", passwordEncoder);
            User bob = createUser("bob", "bob@example.com", "Bob", passwordEncoder);
            User carol = createUser("carol", "carol@example.com", "Carol", passwordEncoder);
            User david = createUser("david", "david@example.com", "David", passwordEncoder);

            List<User> users = List.of(alice, bob, carol, david);
            userRepository.saveAll(users);

            // Create Offers
            Offer offer1 = createOffer(
                    alice, java, OfferType.TEACH,
                    "I'm a senior Java developer with 10 years of experience. I can help you learn Java from basics to advanced topics including Spring Boot.",
                    90, LocationType.ONLINE, "Zoom"
            );

            Offer offer2 = createOffer(
                    bob, python, OfferType.TEACH,
                    "Python expert offering lessons in data science, machine learning, and web development with Django.",
                    60, LocationType.HYBRID, "Sofia or Online"
            );

            Offer offer3 = createOffer(
                    carol, webDesign, OfferType.REQUEST,
                    "Looking to learn modern web design principles. Interested in UI/UX best practices and Figma.",
                    60, LocationType.ONLINE, "Google Meet"
            );

            Offer offer4 = createOffer(
                    david, guitar, OfferType.TEACH,
                    "Guitar teacher with 15 years of experience. Can teach acoustic, electric, and classical guitar.",
                    45, LocationType.IN_PERSON, "Sofia Center"
            );

            Offer offer5 = createOffer(
                    alice, react, OfferType.REQUEST,
                    "Want to learn React hooks and state management. Looking for someone experienced with Redux.",
                    75, LocationType.ONLINE, "Discord"
            );

            Offer offer6 = createOffer(
                    bob, spanish, OfferType.TEACH,
                    "Native Spanish speaker offering conversation practice. All levels welcome!",
                    30, LocationType.ONLINE, "Skype"
            );

            Offer offer7 = createOffer(
                    carol, cooking101, OfferType.TEACH,
                    "Learn to cook authentic Italian dishes. From pasta to tiramisu, I'll teach you family recipes!",
                    120, LocationType.IN_PERSON, "My Kitchen, Sofia"
            );

            Offer offer8 = createOffer(
                    david, yoga, OfferType.TEACH,
                    "Certified yoga instructor offering beginner-friendly sessions. Focus on flexibility and mindfulness.",
                    60, LocationType.HYBRID, "Park or Online"
            );

            List<Offer> offers = List.of(offer1, offer2, offer3, offer4, offer5, offer6, offer7, offer8);
            offerRepository.saveAll(offers);

            System.out.println("✅ Database seeded successfully!");
            System.out.println("   - " + categories.size() + " categories");
            System.out.println("   - " + skills.size() + " skills");
            System.out.println("   - " + users.size() + " users");
            System.out.println("   - " + offers.size() + " offers");
            System.out.println("\nSample credentials:");
            System.out.println("   Username: alice | Password: password");
            System.out.println("   Username: bob   | Password: password");
        };
    }

    private Category createCategory(String name, String description) {
        return Category.builder()
                .name(name)
                .description(description)
                .build();
    }

    private Skill createSkill(String name, Category category, String description) {
        return Skill.builder()
                .name(name)
                .category(category)
                .description(description)
                .build();
    }

    private User createUser(String username, String email, String firstName, PasswordEncoder passwordEncoder) {
        return User.builder()
                .username(username)
                .email(email)
                .firstName(firstName)
                .password(passwordEncoder.encode("password"))
                .role(UserRole.USER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .isDeleted(false)
                .build();
    }

    private Offer createOffer(User owner, Skill skill, OfferType type, String description,
                              int duration, LocationType locationType, String location) {
        return Offer.builder()
                .owner(owner)
                .skill(skill)
                .offerType(type)
                .status(OfferStatus.ACTIVE)
                .description(description)
                .durationMinutes(duration)
                .locationType(locationType)
                .location(location)
                .createdAt(LocalDateTime.now().minusDays((long) (Math.random() * 7)))
                .updatedAt(LocalDateTime.now())
                .build();
    }
}