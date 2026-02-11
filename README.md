# ASCII Mirror

A Java application that searches for ASCII art files, processes their alignment, and generates a symmetrical mirrored reflection.

## 🚀 Key Features
* **Dynamic File Discovery:** Uses `Files.walk` and `System.getProperty("user.dir")` to locate files across the project directory tree automatically, making the app portable across different machines.
* **Symmetrical Alignment:** Implements logic to calculate the maximum line length and applies precise padding to ensure the mirror reflection is perfectly aligned.
* **Character Transformation:** Features a custom mapping engine to flip asymmetrical characters (e.g., `<` to `>`, `[` to `]`) for a true reflection effect.

## 🛠 Tech Stack
* **Language:** Java 11+
* **File I/O:** Java NIO.2 (`Path`, `Files`)
* **Functional Programming:** Java Streams for data transformation and collection.

## 🏛 Architectural Decisions

### Separation of Concerns (Primary Version)
The main version of this project follows the **Single Responsibility Principle**. By breaking the logic into distinct methods like `getMirrorImage()`, `getLongestString()`, and `getLogicalOpposite()`, the code remains highly maintainable, readable, and easy to debug.

### Efficiency vs. Overhead
* **2D Array Mapping:** A 2D `char` array was chosen for character opposites over a `HashMap`. Given the small, fixed set of ASCII pairs, the array loop provides excellent performance with minimal memory overhead.
* **StringBuilder:** Utilized for all string reversals to ensure efficiency and avoid unnecessary memory allocation associated with immutable String concatenation.

---

## 📂 Implementation Variants

In this repository, I have included two distinct approaches to the mirroring logic to demonstrate different problem-solving philosophies:

1. **Main Implementation (Modular):** This is the primary version of the code. It prioritizes **readability and transparency**. By separating the "how" from the "what," it creates a codebase that is intuitive for a team to maintain.
2. **[Stream-Heavy Alternative](./ASCII%20Mirror/task/src/asciimirror/AltStreamsVariant.java):** This version leverages Java Streams more aggressively (e.g., using `.map()` and `.toList()` for the entire transformation pipeline).
    * **Why include it?** It demonstrates proficiency with modern declarative Java and shows how complex logic can be condensed into a concise, functional pipeline.
    * **The Choice:** While the Stream version is concise, I opted for the Modular version as the primary entry point because it better illustrates structural design and intent.

---

### How to Run
1. Clone the repository.
2. Ensure you have a `.txt` file containing ASCII art within the project directory.
3. Compile and run `Main.java`.
4. Input the filename (e.g., `cow.txt`) when prompted.
