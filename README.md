# La Miga Bilingue Bakery 🥖

A bilingual bakery website with support for English and Spanish languages.

## 🌟 Features

- 🌍 **Bilingual Interface** - Switch between English and Spanish
- 🛍️ **Product Catalog** - Display products with prices and descriptions
- 📧 **Contact Form** - Send messages via email
- 📱 **Responsive Design** - Works on all devices
- 🎨 **Modern UI** - Built with shadcn/ui and Tailwind CSS

## 🛠️ Tech Stack

### Frontend
- **React 18** - UI library
- **TypeScript** - Type safety
- **Vite** - Build tool and dev server
- **React Router** - Routing
- **TanStack Query** - State management and data fetching
- **shadcn/ui** - UI components
- **Tailwind CSS** - Styling
- **React Hook Form + Zod** - Forms and validation

### Backend
- **Spring Boot 3.5.7** - Java framework
- **Java 21** - Programming language
- **Gradle** - Build system
- **Spring Mail** - Email sending (optional)

## 📁 Project Structure

```
lamiga-bilingue-bakery/
├── backend/                 # Spring Boot backend
│   ├── src/
│   │   ├── main/java/com/lamiga/
│   │   │   ├── ApiController.java    # REST API endpoints
│   │   │   ├── ProductService.java   # Product service
│   │   │   ├── EmailService.java     # Email service
│   │   │   ├── CorsConfig.java        # CORS configuration
│   │   │   └── Product.java           # Product model
│   │   └── resources/
│   │       └── application.properties # Configuration
│   ├── Dockerfile           # Docker image for deployment
│   └── build.gradle         # Gradle dependencies
│
├── src/                     # Frontend React application
│   ├── pages/              # Application pages
│   │   ├── Home.tsx        # Home page
│   │   ├── About.tsx       # About us
│   │   ├── Products.tsx    # Product catalog
│   │   ├── Contact.tsx     # Contact form
│   │   └── NotFound.tsx    # 404 page
│   ├── components/         # React components
│   │   ├── Navbar.tsx      # Navigation
│   │   ├── Footer.tsx      # Footer
│   │   └── ui/             # UI components (shadcn/ui)
│   ├── contexts/           # React contexts
│   │   └── LanguageContext.tsx  # Language management
│   ├── lib/                # Utilities
│   │   ├── api.ts          # API client
│   │   └── utils.ts        # Helper functions
│   └── assets/             # Images
│
├── package.json            # Frontend dependencies
├── vite.config.ts          # Vite configuration
├── vercel.json             # Vercel configuration
└── render.yaml             # Render Blueprint for backend
```

## 🚀 Quick Start

### Prerequisites

- **Node.js** 18+ and npm
- **Java 21** and Gradle (for backend)
- **Git**

### Frontend Setup

```bash
# Clone the repository
git clone <repository-url>
cd lamiga-bilingue-bakery

# Install dependencies
npm install

# Start dev server (port 3000)
npm run dev

# Build for production
npm run build

# Preview production build
npm run preview
```

### Backend Setup

```bash
# Navigate to backend directory
cd backend

# Build the project (Gradle will automatically download dependencies)
./gradlew build

# Run the application (port 8081)
./gradlew bootRun

# Or use the built JAR
java -jar build/libs/la-miga-backend-0.0.1-SNAPSHOT.jar
```

### Local Development (Frontend + Backend)

1. **Start the backend:**
   ```bash
   cd backend
   ./gradlew bootRun
   ```
   Backend will be available at `http://localhost:8081`

2. **Start the frontend:**
   ```bash
   npm run dev
   ```
   Frontend will be available at `http://localhost:3000`

3. **Configure environment variables:**
   Create a `.env` file in the project root:
   ```env
   VITE_API_URL=http://localhost:8081
   ```

## 🌐 Deployment

### Frontend on Vercel

1. Connect your repository to Vercel
2. Configure environment variables:
   - `VITE_API_URL` - Backend URL (e.g., `https://lamiga-bilingue-bakery-2.onrender.com`)
3. Vercel will automatically detect settings:
   - **Build Command:** `npm run build`
   - **Output Directory:** `dist`
   - **Install Command:** `npm install`

### Backend on Render

#### Option 1: Using Render Blueprint (Recommended)

1. In Render Dashboard, select "New" → "Blueprint"
2. Connect your repository
3. Render will automatically create the service based on `render.yaml`

#### Option 2: Manual Setup

1. In Render Dashboard, select "New" → "Web Service"
2. Connect your repository
3. Configure:
   - **Name:** `la-miga-backend`
   - **Root Directory:** `backend`
   - **Environment:** `Docker`
   - **Dockerfile Path:** `backend/Dockerfile`
   - **Port:** `8080` (Render automatically sets `PORT` variable)

4. Add environment variables (optional, for email):
   ```
   CONTACT_EMAIL_ENABLED=true
   CONTACT_EMAIL_TO=your-email@gmail.com
   CONTACT_EMAIL_FROM=your-email@gmail.com
   SPRING_MAIL_HOST=smtp.gmail.com
   SPRING_MAIL_PORT=587
   SPRING_MAIL_USERNAME=your-email@gmail.com
   SPRING_MAIL_PASSWORD=your-app-password
   SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH=true
   SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE=true
   ```

For detailed email setup instructions, see [backend/EMAIL_SETUP.md](backend/EMAIL_SETUP.md)

## 🔧 Environment Variables

### Frontend (.env)

```env
# Backend URL (required for production)
VITE_API_URL=https://lamiga-bilingue-bakery-2.onrender.com
```

### Backend (application.properties or Environment Variables)

```properties
# Server port
server.port=8081

# Email settings (optional)
contact.email.to=${CONTACT_EMAIL_TO:}
contact.email.from=${CONTACT_EMAIL_FROM:}
contact.email.enabled=${CONTACT_EMAIL_ENABLED:false}

# Spring Mail settings (optional)
spring.mail.host=${SPRING_MAIL_HOST:}
spring.mail.port=${SPRING_MAIL_PORT:587}
spring.mail.username=${SPRING_MAIL_USERNAME:}
spring.mail.password=${SPRING_MAIL_PASSWORD:}
spring.mail.properties.mail.smtp.auth=${SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH:true}
spring.mail.properties.mail.smtp.starttls.enable=${SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE:true}
```

## 📡 API Endpoints

### GET `/api/products`
Returns a list of all products.

**Response:**
```json
[
  {
    "id": 1,
    "name_en": "Sourdough Bread",
    "name_es": "Pan de masa madre",
    "description_en": "Naturally leavened bread with crispy crust",
    "description_es": "Pan de masa madre con corteza crujiente",
    "price": 3.5,
    "imageUrl": "/img/sourdough.jpg"
  }
]
```

### POST `/api/contact`
Sends a message from the contact form.

**Request:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "message": "Hello, I would like to order..."
}
```

**Response:**
```json
{
  "ok": true,
  "message": "Message received successfully"
}
```

### GET `/api/health`
Health check endpoint.

**Response:**
```json
{
  "status": "ok",
  "service": "la-miga-backend",
  "timestamp": "2024-01-01T00:00:00Z"
}
```

## 🎨 Customization

### Adding a New Product

Edit `backend/src/main/java/com/lamiga/ProductService.java`:

```java
new Product(
    6L, 
    "New Product", 
    "Nuevo Producto",
    "English description",
    "Descripción en español",
    2.50, 
    "/img/new-product.jpg"
)
```

### Changing Languages

Languages are managed through `src/contexts/LanguageContext.tsx`. By default, the following are supported:
- `en` - English
- `es` - Spanish

To add a new language:
1. Update the `Language` type in `LanguageContext.tsx`
2. Add translations in components using the `t()` function

## 🐛 Troubleshooting

### Frontend not loading products

1. Check that the backend is running and accessible
2. Ensure `VITE_API_URL` is configured correctly
3. Check CORS settings in `backend/src/main/java/com/lamiga/CorsConfig.java`
4. Open browser console (F12) to view errors

### CORS errors

Make sure the frontend domain is added to `CorsConfig.java`:
```java
.allowedOriginPatterns(
    "http://localhost:*",
    "https://*.vercel.app",
    "https://your-domain.vercel.app"
)
```

### Email not sending

1. Check environment variable settings on Render
2. For Gmail, use App Password (not regular password)
3. Check Render logs for error details
4. See [backend/EMAIL_SETUP.md](backend/EMAIL_SETUP.md) for detailed instructions

## 📝 Scripts

### Frontend
- `npm run dev` - Start dev server
- `npm run build` - Build for production
- `npm run preview` - Preview production build
- `npm run lint` - Run linter

### Backend
- `./gradlew build` - Build the project
- `./gradlew bootRun` - Run the application
- `./gradlew test` - Run tests
- `./gradlew clean` - Clean build directory

## 📄 License

This project is created for demonstration purposes.

## 👥 Authors

La Miga Bilingue Bakery Team

---

**Happy coding! 🥖✨**
