# Uranus Memory Allocator

This repository contains the source code for Uranus Memory Allocator, a memory management application. The application allows users to allocate and deallocate memory segments, manage processes, and visualize the memory layout.

## Features

- Memory allocation using different algorithms (First Fit, Best Fit, etc)
- Visualization of memory layout with address diagrams and segment cells
- Addition and deletion of processes and segments
- Support for managing holes in memory

## Technologies Used

The application is built using the following technologies:

- Java
- JavaFX for the graphical user interface (GUI)
- FXML for defining the GUI layout
- CSS for styling the GUI
- Gradle for dependency management

## Getting Started

To run the Uranus Memory Allocator application locally, follow these steps:

1. Ensure you have Java Development Kit (JDK) installed on your machine.
2. Clone this repository to your local machine using `git clone https://github.com/your-username/uranus-memory-allocator.git`
3. Open the project in your preferred Java IDE.
4. Build the project using Gradle or your IDE's build system.
5. Run the `Main` class to start the application.

## Usage

Once the application is running, you can use the GUI to perform various memory management operations:

- Allocate memory segments for processes by specifying the process ID, segment ID, and size.
- Deallocate memory segments by double-clicking on the segment cell in the memory diagram.
- Add holes to the memory by specifying the base address and size.
- Change the memory size by entering a new value in the memory size text field.
- Choose between different allocation algorithms (First Fit, Best Fit) using the algorithm choice box.
- View the list of processes and their memory allocations in the Processes view.
- View a maximized memory diagram showing the complete memory layout in the Memory view.

## Architecture

The application follows a Model-View-Controller (MVC) architecture, with separate classes for data models, GUI views, and controllers.

- `Segment`: Represents a memory segment with attributes such as segment ID, process ID, base address, and size.
- `SegmentCellFactory`: Creates graphical representations (cells) for memory segments in the memory diagram.
- `AddressDiagram`: Builds address diagrams for memory segments based on the list of segments and total memory size.
- `MemoryCanvas`: Displays the memory layout for processes, including their segments and address diagrams.
- `Controller`: Handles user interactions and manages the application's memory allocation and deallocation logic.
- `Main`: The entry point of the application, sets up the GUI and starts the program.

