import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Desktop;
import java.io.File;
import java.util.Iterator;


public class Prisoner {
    int id;
    String name;
    String mugshotpath;
    int crimeyears;
    int cell;
    ArrayList<String> crimes = new ArrayList<>();
    boolean deathrow;
    boolean parole;

    public Prisoner(int id, String name, String mugshotpath, int crimeyears, ArrayList<String> crimes, int cell, boolean deathrow, boolean parole) {
        this.id = id;
        this.name = name;
        this.crimes = crimes;
        this.deathrow = deathrow;
        this.parole = parole;
        this.crimeyears = crimeyears;
        this.mugshotpath = mugshotpath;
        this.cell = cell;
    }

    void updatesentence(int crimeyears) {
        this.crimeyears = crimeyears;
    }

    void displayinfo() {
        System.out.println("The prisoner's id is " + id);
        System.out.println("The name is " + name);
        for (String crime : crimes) {
            System.out.println("Crime: " + crime);
        }
        System.out.println("The cell number is " + cell);
        if (deathrow) {
            System.out.println("The prisoner is on death row.");
        } else {
            System.out.println("Crime years left: " + crimeyears);
        }
        if(parole) {
            System.out.println("The prisoner is on parole.");
        }
    }

    void movecell(int cellno) {
        this.cell = cellno;
    }

    void addcrime(String crime) {
        this.crimes.add(crime);
    }

    void removecrime(String crime) {
        this.crimes.remove(crime);
    }

    void updatemugshot(String mugshotpath) {
        this.mugshotpath = mugshotpath;
    }

    void updatedeathrow(boolean deathrow) {
        this.deathrow = deathrow;
    }

    void viewMugshot() {
        try {
            File img = new File(mugshotpath);
            if (img.exists()) {
                Desktop.getDesktop().open(img);
            } else {
                System.out.println("Image not found");
            }
        } catch (Exception e) {
            System.out.println("Error opening image");
        }
    }

    static Prisoner searchById(ArrayList<Prisoner> prisoners, int id) {
        for (Prisoner p : prisoners) {
            if (p.id == id) return p;
        }
        return null;
    }

    static Prisoner searchByname(ArrayList<Prisoner> prisoners, String name) {
        for (Prisoner p : prisoners) {
            if (p.name.equalsIgnoreCase(name)) return p;
        }
        return null;
    }

    static boolean searchBycell(ArrayList<Prisoner> prisoners, int cell) {
        for (Prisoner p : prisoners) {
            if (p.cell == cell) return true;
        }
        return false;
    }

    static void displaydeathrow(ArrayList<Prisoner> prisoners) {
        for (Prisoner p : prisoners) {
            if (p.deathrow) {
                p.displayinfo();
                p.viewMugshot();
            }
        }
    }


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int id;
        String name;
        String mugshotpath;
        int crimeyears;
        int cell;
        ArrayList<Prisoner> prisoners = new ArrayList<>();
        ArrayList<Prisoner> releasedPrisoners = new ArrayList<>();

        while (true) {
            System.out.println("______________________________");
            System.out.println("        Prison's System       ");
            System.out.println("______________________________");
            System.out.println("\n1. Add prisoner");
            System.out.println("2. Show prisoners");
            System.out.println("3. Update status");
            System.out.println("4. Look up a prisoner");
            System.out.println("5. Move cells");
            System.out.println("6. Death row prisoners");
            System.out.println("7. Release prisoner");
            System.out.println("8. Released prisoners");   
            System.out.println("9. Exit");
            System.out.println("____________________________");
            System.out.print("\n Choice: ");

            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    // -- ID --
                    System.out.println("Enter the ID: ");
                    id = input.nextInt();
                    input.nextLine();
                    Prisoner p = searchById(prisoners, id);
                    while (p != null) {
                        System.out.println("This ID is already taken!");
                        System.out.println("Enter the ID: ");
                        id = input.nextInt();
                        input.nextLine();
                        p = searchById(prisoners, id);
                    }

                    // -- Cell --
                    System.out.println("Enter the cell number: ");
                    cell = input.nextInt();
                    input.nextLine();
                    boolean occupied = searchBycell(prisoners, cell);
                    while (occupied) {
                        System.out.println("Occupied cell!");
                        System.out.println("Enter the cell number: ");
                        cell = input.nextInt();
                        input.nextLine();
                        occupied = searchBycell(prisoners, cell);
                    }

                    // -- Name --
                    System.out.println("Enter the name: ");
                    name = input.nextLine();

                    // -- Crimes --  
                    ArrayList<String> crimes = new ArrayList<>();
                    System.out.println("Crime count: ");
                    int crimeCount = input.nextInt();
                    input.nextLine();
                    for (int i = 0; i < crimeCount; i++) {
                        System.out.println("Enter crime " + (i + 1) + ": ");
                        crimes.add(input.nextLine());
                    }

                    // -- Death row --
                    System.out.println("Is the prisoner on death row? (true/false): ");
                    boolean deathrow = input.nextBoolean();
                    input.nextLine();

                    // -- Sentence --
                    if(!deathrow) {
                        System.out.println("Enter the current sentence (years): ");
                        crimeyears = input.nextInt();
                        input.nextLine();
                    }
                    else {
                        crimeyears = 0; // Not applicable for death row prisoners
                    }

                    // -- Mugshot --
                    System.out.println("Enter the mugshot path: ");
                    mugshotpath = input.nextLine();

                    // -- Create and add prisoner --

                    System.out.println("Is the prisoner on parole? (true/false): ");
                    boolean parole = input.nextBoolean();
                    input.nextLine();

                    Prisoner prisoner = new Prisoner(id, name, mugshotpath, crimeyears, crimes, cell, deathrow, parole);
                    prisoners.add(prisoner);
                    System.out.println("Prisoner added!");
                    break;

                case 2:
                    if (prisoners.isEmpty()) System.out.println("No prisoners.");
                    else {
                        for (Prisoner c : prisoners) {
                            c.displayinfo();
                            c.viewMugshot();
                        }
                    }
                    break;

                case 3:
                    System.out.println("Enter the ID: ");
                    id = input.nextInt();
                    input.nextLine();
                    p = searchById(prisoners, id);
                    if (p != null) {
                        System.out.println("What do you want to update?");
                        System.out.println("1. Crimes  2. Sentence  3. Mugshot  4. Death row status  5. Parole status");
                        int updateoption = input.nextInt();
                        input.nextLine();

                        // -- Update crimes --
                        if (updateoption == 1) {
                            System.out.println("1. Add a crime  2. Remove a crime");
                            int crimeOption = input.nextInt();
                            input.nextLine();
                            if (crimeOption == 1) {
                                System.out.println("Enter the new crime: ");
                                p.addcrime(input.nextLine());
                                System.out.println("Crime added!");
                            } else if (crimeOption == 2) {
                                System.out.println("Current crimes: " + p.crimes);
                                System.out.println("Enter the crime to remove: ");
                                p.removecrime(input.nextLine());
                                System.out.println("Crime removed!");
                            }
                        }
                        
                        // -- Update sentence --
                        else if (updateoption == 2) {
                            System.out.println("Enter the new sentence: ");
                            crimeyears = input.nextInt();
                            input.nextLine();
                            p.updatesentence(crimeyears);
                            System.out.println("Sentence updated!");
                        }
                        
                        // -- Update mugshot --
                        else if (updateoption == 3) {
                            System.out.println("Enter the new mugshot path: ");
                            mugshotpath = input.nextLine();
                            p.updatemugshot(mugshotpath);
                            System.out.println("Mugshot updated!");
                        } 

                        // -- Update death row status --
                        else if (updateoption == 4) {
                            System.out.println("Is the prisoner on death row? (true/false): ");
                            deathrow = input.nextBoolean();
                            input.nextLine();
                            p.updatedeathrow(deathrow);
                            System.out.println("Death row status updated!");
                        }
                        else if (updateoption == 5) {
                            System.out.println("Is the prisoner on parole? (true/false): ");
                            parole = input.nextBoolean();
                            input.nextLine();
                            p.parole = parole;
                            System.out.println("Parole status updated!");
                        }
                    } else {
                        System.out.println("This ID doesn't exist!");  
                    }
                    break;

                case 4:
                    // -- Look up by ID or name --
                    System.out.println("By: 1. ID  2. Name");
                    int option = input.nextInt();
                    input.nextLine();

                    // -- Look up by ID--
                    if (option == 1) {
                        System.out.println("Enter the ID: ");
                        id = input.nextInt();
                        input.nextLine();
                        p = searchById(prisoners, id);
                        if (p != null) {
                            p.displayinfo();
                            p.viewMugshot();
                        } else {
                            System.out.println("This ID doesn't exist!");
                        }
                    }
                    
                    // -- Look up by name --
                    else if (option == 2) {
                        System.out.println("Enter the name: ");
                        name = input.nextLine();
                        p = searchByname(prisoners, name);
                        if (p != null) {
                            p.displayinfo();
                            p.viewMugshot();
                        } else {
                            System.out.println("There is no prisoner with this name!");
                        }
                    }
                    break;

                case 5:
                    // -- Move cells --
                    System.out.println("Enter the ID: ");
                    id = input.nextInt();
                    input.nextLine();
                    p = searchById(prisoners, id);
                    if (p == null) {
                        System.out.println("This ID doesn't exist!");
                        break;
                    }
                    System.out.println("Enter the new cell: ");
                    cell = input.nextInt();
                    input.nextLine();
                    occupied = searchBycell(prisoners, cell);
                    if (occupied) {
                        System.out.println("Occupied cell!");
                        break;
                    }
                    p.movecell(cell);
                    System.out.println("Prisoner no." + id + " is moved to cell no." + cell);
                    break;

                case 6:
                    // -- Display death row prisoners --
                    displaydeathrow(prisoners);
                    break;
                case 7:
                    // -- Release prisoner --
                    System.out.println("Enter the ID: ");
                    id = input.nextInt();
                    input.nextLine();
                    p = searchById(prisoners, id);
                    if (p != null) {
                        prisoners.remove(p);
                        releasedPrisoners.add(p);
                        System.out.println("Prisoner no." + p.id + " is released!");
                    } else {
                        System.out.println("This ID doesn't exist!");
                    }

                    Iterator<Prisoner> iterator = prisoners.iterator();
                    while (iterator.hasNext()) {
                        Prisoner r = iterator.next();
                        if(r.crimeyears == 0 && !r.deathrow) {
                            iterator.remove();
                            releasedPrisoners.add(r);
                            System.out.println("Prisoner no." + r.id + " is released!");
                        }
                    }
                    break;
                case 8:
                    // -- Display released prisoners --
                    if (releasedPrisoners.isEmpty()) System.out.println("No released prisoners.");
                    else {
                        for (Prisoner r : releasedPrisoners) {
                            r.displayinfo();
                            r.viewMugshot();
                        }
                    }
                    break;

                case 9:
                    //-- Exit --
                    System.out.println("Exiting...");
                    input.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option, please try again.");
                    break;
            }
        }
    }
}