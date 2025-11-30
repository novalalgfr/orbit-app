# 🪐 Orbit - Project Management System

**Orbit** adalah aplikasi desktop modern berbasis Java Swing yang dirancang untuk membantu tim mengelola proyek dan tugas (ticketing) dengan efisien.

Proyek ini dikembangkan sebagai tugas akhir **Praktikum Rekayasa Perangkat Lunak 2**.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)

---

## ✨ Fitur Utama

1.  **Modern UI/UX**: Tampilan antarmuka yang bersih dan modern menggunakan library **FlatLaf**.
2.  **Role-Based Access**:
    * **Admin (Project Manager)**: Bisa mengelola User dan Project.
    * **Member**: Bisa melihat tugas dan update status tiket.
3.  **Project Management**: Membuat, mengedit, dan melihat status proyek.
4.  **Ticket System**:
    * Manajemen tugas (CRUD).
    * Status tracking: `TODO` -> `IN_PROGRESS` -> `DONE`.
    * Priority setting: `HIGH`, `MEDIUM`, `LOW`.
5.  **Dashboard**: Ringkasan statistik proyek dan aktivitas terbaru.

---

## 🛠️ Teknologi yang Digunakan

* **Bahasa Pemrograman**: Java (JDK 17 / 21)
* **GUI Framework**: Java Swing
* **Theme Library**: FlatLaf (Flat Look and Feel)
* **Database**: MySQL
* **ORM**: Hibernate 5.6
* **Dependency Injection**: Spring Context 5.3
* **Build Tool**: Maven

---

## ⚙️ Cara Instalasi & Menjalankan

Ikuti langkah-langkah berikut untuk menjalankan aplikasi di komputer lokal Anda:

### 1. Prasyarat
Pastikan Anda sudah menginstall:
* Java Development Kit (JDK) 17 atau lebih baru.
* Maven.
* MySQL Server (XAMPP/WAMP).
* IDE (VS Code / NetBeans / IntelliJ).

### 2. Setup Database
1.  Buka **phpMyAdmin** atau MySQL Workbench.
2.  Buat database baru dengan nama: **`db_orbit_app`**.
3.  Jalankan script SQL (atau import file sql jika ada) untuk membuat tabel `users`, `projects`, dan `tickets`.
    *(Pastikan struktur tabel sesuai dengan Entity Class di Java)*.

### 3. Konfigurasi Koneksi
Buka file `src/main/resources/hibernate.cfg.xml`. Sesuaikan username dan password database Anda:

```xml
<property name="connection.username">root</property>
<property name="connection.password"></property> ```

### 4. Build & Run
Jika menggunakan **VS Code**:
1.  Buka folder project ini.
2.  Tunggu Maven selesai mendownload dependencies.
3.  Buka file `src/main/java/com/orbit/Main.java`.
4.  Klik **Run**.

Jika menggunakan **Terminal/CMD**:
```bash
mvn clean install
mvn exec:java -Dexec.mainClass="com.orbit.Main"
