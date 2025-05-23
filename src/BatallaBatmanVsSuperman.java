import java.util.Scanner;

class Personaje {
    String nombre;
    int fuerza;
    int velocidad;
    int resistencia;
    int resistenciaMaxima; 

    public Personaje(String nombre, int fuerza, int velocidad, int resistencia) {
        this.nombre = nombre;
        this.fuerza = fuerza;
        this.velocidad = velocidad;
        this.resistencia = resistencia;
        this.resistenciaMaxima = resistencia; 
    }

    public void atacar(Personaje oponente) {
        System.out.println(nombre + " ataca a " + oponente.nombre + " con " + fuerza + " puntos de fuerza.");
        oponente.resistencia -= fuerza;
        if (oponente.resistencia < 0) oponente.resistencia = 0;
        System.out.println(oponente.nombre + " ahora tiene " + oponente.resistencia + " puntos de resistencia.");
    }

    public void mostrarEstadisticas() {
        System.out.println("\n--- Estadísticas de " + nombre + " ---");
        System.out.println("Fuerza: " + fuerza);
        System.out.println("Velocidad: " + velocidad);
        System.out.println("Resistencia: " + resistencia + "/" + resistenciaMaxima);
        System.out.println("---------------------------------------");
    }

    public void recuperarse() {
        resistencia += 20;
        if (resistencia > resistenciaMaxima) {
            resistencia = resistenciaMaxima;
        }
        System.out.println(nombre + " se ha recuperado y ahora tiene " + resistencia + " puntos de resistencia.");
    }
}

class Superman extends Personaje {
    private int cooldownLaser;

    public Superman(String nombre, int fuerza, int velocidad, int resistencia) {
        super(nombre, fuerza, velocidad, resistencia);
        cooldownLaser = 0;
    }

    public void ataqueLaser(Personaje oponente) {
        if (cooldownLaser == 0) {
            int poderLaser = velocidad * 3;
            System.out.println(nombre + " lanza un rayo láser a " + oponente.nombre + " causando " + poderLaser + " puntos de daño.");
            oponente.resistencia -= poderLaser;
            if (oponente.resistencia < 0) oponente.resistencia = 0;
            System.out.println(oponente.nombre + " ahora tiene " + oponente.resistencia + " puntos de resistencia.");
            cooldownLaser = 3; 
        } else {
            System.out.println(nombre + " no puede usar el rayo láser aún. Le quedan " + cooldownLaser + " turnos de espera.");
        }
    }

    public void reducirCooldown() {
        if (cooldownLaser > 0) {
            cooldownLaser--;
        }
    }
}

class Batman extends Personaje {
    public Batman(String nombre, int fuerza, int velocidad, int resistencia) {
        super(nombre, fuerza, velocidad, resistencia);
    }

    public void usarGadget(Personaje oponente) {
        int danoGadget = fuerza + 10;
        System.out.println(nombre + " usa un gadget especial contra " + oponente.nombre + " causando " + danoGadget + " puntos de daño.");
        oponente.resistencia -= danoGadget;
        if (oponente.resistencia < 0) oponente.resistencia = 0;
        System.out.println(oponente.nombre + " ahora tiene " + oponente.resistencia + " puntos de resistencia.");
    }
}

public class BatallaBatmanVsSuperman {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Batman batman = new Batman("Batman", 25, 20, 100);
        Superman superman = new Superman("Superman", 35, 30, 120);

        batman.mostrarEstadisticas();
        superman.mostrarEstadisticas();

        int opcion;
        boolean batallaTerminada = false;

        do {
            System.out.println("\n=======================================");
            System.out.println("Elige una acción:");
            System.out.println("1. Batman ataca a Superman");
            System.out.println("2. Batman usa un gadget");
            System.out.println("3. Superman ataca a Batman");
            System.out.println("4. Superman lanza rayo láser");
            System.out.println("5. Batman se recupera");
            System.out.println("6. Superman se recupera");
            System.out.println("7. Mostrar estadísticas");
            System.out.println("8. Terminar batalla");
            System.out.print("Tu elección: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, ingresa un número válido:");
                scanner.next();
            }

            opcion = scanner.nextInt();

            System.out.println();

            switch (opcion) {
                case 1:
                    batman.atacar(superman);
                    break;
                case 2:
                    batman.usarGadget(superman);
                    break;
                case 3:
                    superman.atacar(batman);
                    break;
                case 4:
                    superman.ataqueLaser(batman);
                    break;
                case 5:
                    batman.recuperarse();
                    break;
                case 6:
                    superman.recuperarse();
                    break;
                case 7:
                    batman.mostrarEstadisticas();
                    superman.mostrarEstadisticas();
                    break;
                case 8:
                    System.out.println("La batalla ha sido terminada por el usuario.");
                    batallaTerminada = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }

            superman.reducirCooldown();

            if (batallaTerminada) {
                break;
            }

            if (batman.resistencia == 0 || superman.resistencia == 0) {
                batallaTerminada = true;
            }

        } while (!batallaTerminada);

        System.out.println("\n=======================================");
        if (batman.resistencia == 0 && superman.resistencia == 0) {
            System.out.println("¡Empate! Ambos personajes han sido derrotados.");
        } else if (batman.resistencia == 0) {
            System.out.println("¡Superman gana la batalla!");
        } else if (superman.resistencia == 0) {
            System.out.println("¡Batman gana la batalla!");
        } else {
            System.out.println("La batalla terminó sin un ganador claro.");
        }

        scanner.close();
    }
}
