import tkinter as tk
from tkinter import messagebox, scrolledtext
import smtplib
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
import threading

def enviar_correo():
    # Desactivar botón mientras envía
    btn_enviar.config(state=tk.DISABLED)
    log_text.insert(tk.END, "[INFO] Preparando envío...\n")
    
    # Recoger datos del formulario
    host = entry_host.get()
    port = entry_port.get()
    user = entry_user.get()
    password = entry_pass.get()
    remitente = entry_from.get()
    destinatario = entry_to.get()
    asunto = entry_subject.get()
    cuerpo_html = text_body.get("1.0", tk.END)

    def thread_envio():
        try:
            log_text.insert(tk.END, f"[INFO] Conectando a {host}:{port}...\n")
            
            # Crear mensaje
            msg = MIMEMultipart()
            msg['From'] = remitente
            msg['To'] = destinatario
            msg['Subject'] = asunto
            msg.attach(MIMEText(cuerpo_html, 'html'))

            # Conexión SMTP
            server = smtplib.SMTP(host, int(port))
            log_text.insert(tk.END, "[INFO] EHLO...\n")
            server.ehlo()
            
            # Mailtrap suele requerir STARTTLS
            log_text.insert(tk.END, "[INFO] STARTTLS...\n")
            server.starttls()
            server.ehlo()

            log_text.insert(tk.END, "[INFO] LOGIN...\n")
            server.login(user, password)

            log_text.insert(tk.END, "[INFO] SEND...\n")
            server.sendmail(remitente, destinatario, msg.as_string())
            
            server.quit()
            log_text.insert(tk.END, "[OK] Mensaje enviado. Revisa el Inbox de Mailtrap.\n")
            log_text.see(tk.END)
            
        except Exception as e:
            log_text.insert(tk.END, f"[ERROR] {str(e)}\n")
        finally:
            btn_enviar.config(state=tk.NORMAL)

    # Ejecutar en un hilo secundario para no congelar la ventana
    threading.Thread(target=thread_envio).start()

def limpiar_log():
    log_text.delete("1.0", tk.END)

# --- Configuración de la Ventana ---
root = tk.Tk()
root.title("Envío SMTP (Mailtrap / Sandbox)")
root.geometry("900x600")

# Frame Configuración SMTP
frame_cfg = tk.LabelFrame(root, text="Configuración SMTP")
frame_cfg.pack(fill="x", padx=10, pady=5)

tk.Label(frame_cfg, text="HOST").grid(row=0, column=0, padx=5, sticky="e")
entry_host = tk.Entry(frame_cfg, width=25)
entry_host.insert(0, "sandbox.smtp.mailtrap.io") # Valor por defecto común
entry_host.grid(row=0, column=1, padx=5, pady=5)

tk.Label(frame_cfg, text="PORT").grid(row=0, column=2, padx=5, sticky="e")
entry_port = tk.Entry(frame_cfg, width=10)
entry_port.insert(0, "2525")
entry_port.grid(row=0, column=3, padx=5)

tk.Label(frame_cfg, text="USERNAME").grid(row=1, column=0, padx=5, sticky="e")
entry_user = tk.Entry(frame_cfg, width=25)
entry_user.grid(row=1, column=1, padx=5, pady=5)

tk.Label(frame_cfg, text="PASSWORD").grid(row=1, column=2, padx=5, sticky="e")
entry_pass = tk.Entry(frame_cfg, width=25, show="*")
entry_pass.grid(row=1, column=3, padx=5)

tk.Label(frame_cfg, text="FROM").grid(row=2, column=0, padx=5, sticky="e")
entry_from = tk.Entry(frame_cfg, width=25)
entry_from.grid(row=2, column=1, padx=5, pady=5)

tk.Label(frame_cfg, text="TO").grid(row=2, column=2, padx=5, sticky="e")
entry_to = tk.Entry(frame_cfg, width=25)
entry_to.grid(row=2, column=3, padx=5)

tk.Label(frame_cfg, text="SUBJECT").grid(row=3, column=0, padx=5, sticky="e")
entry_subject = tk.Entry(frame_cfg, width=55)
entry_subject.grid(row=3, column=1, columnspan=3, padx=5, pady=5, sticky="w")

# Frame Cuerpo y Log
frame_body_log = tk.Frame(root)
frame_body_log.pack(fill="both", expand=True, padx=10, pady=5)

# Izquierda: Cuerpo HTML
frame_left = tk.LabelFrame(frame_body_log, text="Cuerpo del correo (HTML)")
frame_left.pack(side="left", fill="both", expand=True, padx=(0,5))
text_body = scrolledtext.ScrolledText(frame_left, height=15)
text_body.pack(fill="both", expand=True, padx=5, pady=5)
# Texto por defecto
text_body.insert(tk.END, "<html>\n<body>\n<h2>Hola</h2>\n<p>Este es un correo de prueba enviado desde <b>Python</b>.</p>\n</body>\n</html>")

# Derecha: Log
frame_right = tk.LabelFrame(frame_body_log, text="Salida / Log")
frame_right.pack(side="right", fill="both", expand=True, padx=(5,0))
log_text = scrolledtext.ScrolledText(frame_right, height=15, state="normal")
log_text.pack(fill="both", expand=True, padx=5, pady=5)

# Botones inferiores
frame_btns = tk.Frame(root)
frame_btns.pack(fill="x", padx=10, pady=10)

btn_enviar = tk.Button(frame_btns, text="ENVIAR", bg="#a40000", fg="white", font=("Arial", 10, "bold"), command=enviar_correo)
btn_enviar.pack(side="right")

btn_limpiar = tk.Button(frame_btns, text="Limpiar log", command=limpiar_log)
btn_limpiar.pack(side="right", padx=10)

root.mainloop()