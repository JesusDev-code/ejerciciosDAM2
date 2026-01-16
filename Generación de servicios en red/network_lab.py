import tkinter as tk
from tkinter import ttk, scrolledtext
import socket
import ntplib # Requiere: pip install ntplib
from time import ctime

# --- Lógica DNS ---
def resolver_dns():
    target = entry_dns.get().strip()
    log_dns.insert(tk.END, f"Resolviendo: {target}...\n")
    try:
        # Detectar si es una IP para hacer búsqueda inversa, o un nombre para buscar IP
        try:
            # Si es una IP válida, esto funcionará y haremos búsqueda inversa
            socket.inet_aton(target)
            es_ip = True
        except socket.error:
            es_ip = False

        if es_ip:
            # Búsqueda Inversa (IP -> Nombre)
            resultado = socket.gethostbyaddr(target)
            log_dns.insert(tk.END, f"[OK] Nombre del host: {resultado[0]}\n")
            log_dns.insert(tk.END, f"[INFO] Alias: {resultado[1]}\n")
            log_dns.insert(tk.END, f"[INFO] IPs asociadas: {resultado[2]}\n")
        else:
            # Búsqueda Directa (Nombre -> IP)
            ip = socket.gethostbyname(target)
            log_dns.insert(tk.END, f"[OK] La IP de {target} es {ip}\n")
            # Info extendida
            info = socket.getaddrinfo(target, 80)
            for item in info:
                 # item[4] contiene la tupla (IP, puerto)
                 log_dns.insert(tk.END, f"   -> Info: {item[4][0]}\n")
                 
    except socket.gaierror:
        log_dns.insert(tk.END, "[ERROR] No se pudo resolver el host. Verifica el nombre.\n")
    except Exception as e:
        log_dns.insert(tk.END, f"[ERROR] {str(e)}\n")
    log_dns.insert(tk.END, "-"*40 + "\n")
    log_dns.see(tk.END)

# --- Lógica NTP ---
def consultar_ntp():
    servidor_ntp = entry_ntp.get()
    log_ntp.insert(tk.END, f"[INFO] Consultando servidor: {servidor_ntp}...\n")
    
    try:
        cliente = ntplib.NTPClient()
        # Hacemos la petición al servidor NTP
        respuesta = cliente.request(servidor_ntp, version=3)
        
        # Convertimos la hora recibida a formato legible
        hora_oficial = ctime(respuesta.tx_time)
        
        log_ntp.insert(tk.END, f"[OK] Hora exacta: {hora_oficial}\n")
        log_ntp.insert(tk.END, f"[DATA] Stratum (nivel): {respuesta.stratum}\n")
        log_ntp.insert(tk.END, f"[DATA] Retardo (delay): {respuesta.delay}\n")
        
    except Exception as e:
        log_ntp.insert(tk.END, f"[ERROR] Fallo al conectar NTP: {str(e)}\n")
        log_ntp.insert(tk.END, "[HINT] Si falla en la escuela, puede que el puerto UDP 123 esté bloqueado.\n")
    
    log_ntp.insert(tk.END, "-"*40 + "\n")
    log_ntp.see(tk.END)

# --- Interfaz Gráfica ---
root = tk.Tk()
root.title("NetworkLab - DNS & NTP")
root.geometry("600x450")

# Crear el sistema de pestañas
notebook = ttk.Notebook(root)
notebook.pack(pady=10, fill='both', expand=True)

# --- Pestaña 1: DNS ---
frame_dns = tk.Frame(notebook)
notebook.add(frame_dns, text='   Consulta DNS   ')

tk.Label(frame_dns, text="Dominio o IP (ej: google.com):", font=("Arial", 10, "bold")).pack(pady=(15, 5))
entry_dns = tk.Entry(frame_dns, width=40, font=("Arial", 10))
entry_dns.insert(0, "www.google.com")
entry_dns.pack(pady=5)

btn_dns = tk.Button(frame_dns, text="RESOLVER DNS", bg="#2c3e50", fg="white", command=resolver_dns)
btn_dns.pack(pady=5)

log_dns = scrolledtext.ScrolledText(frame_dns, height=12, bg="#f0f0f0")
log_dns.pack(fill="both", expand=True, padx=15, pady=10)

# --- Pestaña 2: NTP ---
frame_ntp = tk.Frame(notebook)
notebook.add(frame_ntp, text='   Reloj NTP   ')

tk.Label(frame_ntp, text="Servidor NTP (ej: pool.ntp.org):", font=("Arial", 10, "bold")).pack(pady=(15, 5))
entry_ntp = tk.Entry(frame_ntp, width=40, font=("Arial", 10))
entry_ntp.insert(0, "pool.ntp.org")
entry_ntp.pack(pady=5)

btn_ntp = tk.Button(frame_ntp, text="OBTENER HORA", bg="#a40000", fg="white", command=consultar_ntp)
btn_ntp.pack(pady=5)

log_ntp = scrolledtext.ScrolledText(frame_ntp, height=12, bg="#f0f0f0")
log_ntp.pack(fill="both", expand=True, padx=15, pady=10)

root.mainloop()