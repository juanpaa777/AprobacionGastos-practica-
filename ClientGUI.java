import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import handlers.*;
import model.Request;

public class ClientGUI extends JFrame {
    private JTextField campoDescripcion, campoMonto, campoSolicitante;
    private JTextArea areaResultados;
    private JLabel etiquetaEstado;
    private JPanel panelSupervisor, panelGerente, panelDirector, panelCEO;
    private JLabel labelEstadoSupervisor, labelEstadoGerente, labelEstadoDirector, labelEstadoCEO;
    
    private final Color COLOR_INACTIVO = new Color(240, 240, 240);
    private final Color COLOR_EVALUANDO = new Color(255, 255, 180);
    private final Color COLOR_APROBADO = new Color(144, 238, 144);
    private final Color COLOR_DELEGANDO = new Color(255, 200, 100);
    
    public ClientGUI() {
        setTitle("Sistema de Aprobación de Gastos - Chain of Responsibility");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        
        crearInterfaz();
        mostrarInfoInicial();
    }
    
    private void crearInterfaz() {
        setLayout(new BorderLayout(10, 10));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));
        
        // Menú
        JMenuBar menuBar = new JMenuBar();
        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem itemAcercaDe = new JMenuItem("Acerca de...");
        itemAcercaDe.addActionListener(e -> mostrarAcercaDe());
        menuAyuda.add(itemAcercaDe);
        menuBar.add(menuAyuda);
        setJMenuBar(menuBar);
        
        // Panel superior - Cadena
        add(crearPanelCadena(), BorderLayout.NORTH);
        
        // Panel central - Formulario
        add(crearPanelFormulario(), BorderLayout.CENTER);
        
        // Panel inferior - Resultados
        add(crearPanelResultados(), BorderLayout.SOUTH);
    }
    
    private JPanel crearPanelCadena() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(new TitledBorder("Cadena de Responsabilidad"));
        panel.setPreferredSize(new Dimension(0, 140));
        
        Object[] s = crearPanelHandler("SUPERVISOR", "$1,000", new Color(70, 130, 180));
        panelSupervisor = (JPanel) s[0];
        labelEstadoSupervisor = (JLabel) s[1];
        
        Object[] g = crearPanelHandler("GERENTE", "$5,000", new Color(65, 105, 225));
        panelGerente = (JPanel) g[0];
        labelEstadoGerente = (JLabel) g[1];
        
        Object[] d = crearPanelHandler("DIRECTOR", "$10,000", new Color(30, 144, 255));
        panelDirector = (JPanel) d[0];
        labelEstadoDirector = (JLabel) d[1];
        
        Object[] c = crearPanelHandler("CEO", "Sin limite", new Color(25, 25, 112));
        panelCEO = (JPanel) c[0];
        labelEstadoCEO = (JLabel) c[1];
        
        panel.add(Box.createHorizontalGlue());
        panel.add(panelSupervisor);
        panel.add(crearFlecha());
        panel.add(panelGerente);
        panel.add(crearFlecha());
        panel.add(panelDirector);
        panel.add(crearFlecha());
        panel.add(panelCEO);
        panel.add(Box.createHorizontalGlue());
        
        return panel;
    }
    
    private JLabel crearFlecha() {
        JLabel f = new JLabel("→");
        f.setFont(new Font("Arial", Font.BOLD, 24));
        f.setForeground(new Color(100, 100, 100));
        return f;
    }
    
    private Object[] crearPanelHandler(String nombre, String limite, Color color) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(new LineBorder(color, 3));
        p.setBackground(COLOR_INACTIVO);
        p.setPreferredSize(new Dimension(150, 110));
        
        JLabel l1 = new JLabel(nombre);
        l1.setFont(new Font("Arial", Font.BOLD, 13));
        l1.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel l2 = new JLabel("Limite: " + limite);
        l2.setFont(new Font("Arial", Font.PLAIN, 10));
        l2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel l3 = new JLabel("Esperando...");
        l3.setFont(new Font("Arial", Font.PLAIN, 9));
        l3.setAlignmentX(Component.CENTER_ALIGNMENT);
        l3.setName("estado");
        
        p.add(Box.createVerticalStrut(10));
        p.add(l1);
        p.add(Box.createVerticalStrut(5));
        p.add(l2);
        p.add(Box.createVerticalStrut(5));
        p.add(l3);
        
        return new Object[]{p, l3};
    }
    
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new TitledBorder("Nueva Solicitud"));
        
        // Campos
        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        p1.add(new JLabel("Descripcion:"));
        campoDescripcion = new JTextField(25);
        p1.add(campoDescripcion);
        
        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        p2.add(new JLabel("Monto ($):"));
        campoMonto = new JTextField(25);
        p2.add(campoMonto);
        
        JPanel p3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        p3.add(new JLabel("Solicitante:"));
        campoSolicitante = new JTextField(25);
        p3.add(campoSolicitante);
        
        // Botones
        JPanel botones = new JPanel(new FlowLayout());
        JButton b1 = new JButton("Procesar");
        b1.setBackground(new Color(46, 125, 50));
        b1.setForeground(Color.WHITE);
        b1.addActionListener(e -> procesar());
        
        JButton b2 = new JButton("Limpiar");
        b2.setBackground(new Color(198, 40, 40));
        b2.setForeground(Color.WHITE);
        b2.addActionListener(e -> limpiar());
        
        botones.add(b1);
        botones.add(b2);
        
        etiquetaEstado = new JLabel(" ");
        etiquetaEstado.setHorizontalAlignment(SwingConstants.CENTER);
        
        panel.add(Box.createVerticalStrut(10));
        panel.add(p1);
        panel.add(p2);
        panel.add(p3);
        panel.add(Box.createVerticalStrut(10));
        panel.add(botones);
        panel.add(etiquetaEstado);
        
        return panel;
    }
    
    private JPanel crearPanelResultados() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Resultados"));
        panel.setPreferredSize(new Dimension(0, 200));
        
        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Courier New", Font.PLAIN, 11));
        areaResultados.setBackground(new Color(250, 250, 250));
        
        panel.add(new JScrollPane(areaResultados), BorderLayout.CENTER);
        return panel;
    }
    
    private void procesar() {
        try {
            String desc = campoDescripcion.getText().trim();
            double monto = Double.parseDouble(campoMonto.getText().trim());
            String sol = campoSolicitante.getText().trim();
            
            if (desc.isEmpty() || sol.isEmpty() || monto <= 0) {
                etiquetaEstado.setText("Error: Complete todos los campos");
                etiquetaEstado.setForeground(Color.RED);
                return;
            }
            
            Request req = new Request(desc, monto, sol);
            resetearVisualizacion();
            areaResultados.setText("");
            
            areaResultados.append("Procesando: " + req + "\n\n");
            
            // Crear cadena
            Handler supervisor = new Supervisor();
            Handler gerente = new Gerente();
            Handler director = new Director();
            Handler ceo = new CEO();
            supervisor.setNext(gerente).setNext(director).setNext(ceo);
            
            // Procesar con visualización
            procesarConVisualizacion(supervisor, req);
            
        } catch (Exception e) {
            etiquetaEstado.setText("Error: " + e.getMessage());
            etiquetaEstado.setForeground(Color.RED);
        }
    }
    
    private void procesarConVisualizacion(Handler handler, Request req) {
        new Thread(() -> {
            try {
                // Supervisor
                iluminar("SUPERVISOR", "Evaluando...", COLOR_EVALUANDO);
                Thread.sleep(1000);
                if (req.getMonto() <= 1000) {
                    iluminar("SUPERVISOR", "APROBADO", COLOR_APROBADO);
                    areaResultados.append("APROBADO POR: SUPERVISOR\n");
                    etiquetaEstado.setText("Aprobado por: SUPERVISOR");
                    etiquetaEstado.setForeground(new Color(46, 125, 50));
                    return;
                }
                iluminar("SUPERVISOR", "Delegando...", COLOR_DELEGANDO);
                Thread.sleep(500);
                iluminar("SUPERVISOR", "Delegado", COLOR_INACTIVO);
                
                // Gerente
                iluminar("GERENTE", "Evaluando...", COLOR_EVALUANDO);
                Thread.sleep(1000);
                if (req.getMonto() <= 5000) {
                    iluminar("GERENTE", "APROBADO", COLOR_APROBADO);
                    areaResultados.append("APROBADO POR: GERENTE\n");
                    etiquetaEstado.setText("Aprobado por: GERENTE");
                    etiquetaEstado.setForeground(new Color(46, 125, 50));
                    return;
                }
                iluminar("GERENTE", "Delegando...", COLOR_DELEGANDO);
                Thread.sleep(500);
                iluminar("GERENTE", "Delegado", COLOR_INACTIVO);
                
                // Director
                iluminar("DIRECTOR", "Evaluando...", COLOR_EVALUANDO);
                Thread.sleep(1000);
                if (req.getMonto() <= 10000) {
                    iluminar("DIRECTOR", "APROBADO", COLOR_APROBADO);
                    areaResultados.append("APROBADO POR: DIRECTOR\n");
                    etiquetaEstado.setText("Aprobado por: DIRECTOR");
                    etiquetaEstado.setForeground(new Color(46, 125, 50));
                    return;
                }
                iluminar("DIRECTOR", "Delegando...", COLOR_DELEGANDO);
                Thread.sleep(500);
                iluminar("DIRECTOR", "Delegado", COLOR_INACTIVO);
                
                // CEO
                iluminar("CEO", "Evaluando...", COLOR_EVALUANDO);
                Thread.sleep(1000);
                iluminar("CEO", "APROBADO", COLOR_APROBADO);
                areaResultados.append("APROBADO POR: CEO\n");
                etiquetaEstado.setText("Aprobado por: CEO");
                etiquetaEstado.setForeground(new Color(46, 125, 50));
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
    
    private void iluminar(String nombre, String estado, Color color) {
        SwingUtilities.invokeLater(() -> {
            JPanel panel = null;
            JLabel label = null;
            
            switch (nombre) {
                case "SUPERVISOR": panel = panelSupervisor; label = labelEstadoSupervisor; break;
                case "GERENTE": panel = panelGerente; label = labelEstadoGerente; break;
                case "DIRECTOR": panel = panelDirector; label = labelEstadoDirector; break;
                case "CEO": panel = panelCEO; label = labelEstadoCEO; break;
            }
            
            if (panel != null && label != null) {
                panel.setBackground(color);
                label.setText(estado);
                panel.repaint();
            }
        });
    }
    
    private void resetearVisualizacion() {
        iluminar("SUPERVISOR", "Esperando...", COLOR_INACTIVO);
        iluminar("GERENTE", "Esperando...", COLOR_INACTIVO);
        iluminar("DIRECTOR", "Esperando...", COLOR_INACTIVO);
        iluminar("CEO", "Esperando...", COLOR_INACTIVO);
    }
    
    private void limpiar() {
        campoDescripcion.setText("");
        campoMonto.setText("");
        campoSolicitante.setText("");
        areaResultados.setText("");
        etiquetaEstado.setText(" ");
        resetearVisualizacion();
        mostrarInfoInicial();
    }
    
    private void mostrarInfoInicial() {
        areaResultados.setText("Sistema de Aprobacion de Gastos\n");
        areaResultados.append("Patron: Chain of Responsibility\n\n");
        areaResultados.append("Complete el formulario y haga clic en 'Procesar'\n");
        areaResultados.append("Observe como fluye la solicitud por la cadena\n");
    }
    
    private void mostrarAcercaDe() {
        JDialog dialog = new JDialog(this, "Acerca de", true);
        dialog.setSize(450, 350);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel titulo = new JLabel("Chain of Responsibility", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 14));
        
        JTextArea info = new JTextArea();
        info.setEditable(false);
        info.setFont(new Font("Arial", Font.PLAIN, 11));
        info.setText("Este patron permite que multiples objetos procesen una solicitud.\n\n" +
                    "Cada handler decide si puede procesarla.\n" +
                    "Si no puede, la delega al siguiente.\n\n" +
                    "Ejemplo:\n" +
                    "- Supervisor: hasta $1,000\n" +
                    "- Gerente: hasta $5,000\n" +
                    "- Director: hasta $10,000\n" +
                    "- CEO: sin limite");
        
        JButton cerrar = new JButton("Cerrar");
        cerrar.addActionListener(e -> dialog.dispose());
        
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(new JScrollPane(info), BorderLayout.CENTER);
        panel.add(cerrar, BorderLayout.SOUTH);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> new ClientGUI().setVisible(true));
    }
}

