// Get base URL and fix common errors
const getApiBase = (): string => {
  const envUrl = import.meta.env.VITE_API_URL;
  if (!envUrl) return 'http://localhost:8081';
  
  let url = envUrl.trim().replace(/\/$/, '');
  
  // Fix common error: hyphen at the start of domain
  // la-miga-bilingue-bakery-2.onrender.com -> lamiga-bilingue-bakery-2.onrender.com
  if (url.includes('la-miga-bilingue-bakery-2.onrender.com')) {
    url = url.replace('la-miga-bilingue-bakery-2.onrender.com', 'lamiga-bilingue-bakery-2.onrender.com');
    if (import.meta.env.DEV) {
      console.warn('[API] Fixed incorrect URL: removed hyphen from domain start');
    }
  }
  
  return url;
};

const API_BASE = getApiBase();

const buildUrl = (path: string): string => {
  const url = `${API_BASE}${path}`;
  // Only log in development mode
  if (import.meta.env.DEV) {
    console.log('[API] Request URL:', url);
  }
  return url;
};

export type Product = {
  id: number;
  name_en: string;
  name_es: string;
  description_en: string;
  description_es: string;
  price: number;
  imageUrl: string;
};

export async function getProducts(): Promise<Product[]> {
  const url = buildUrl('/api/products');
  
  try {
    const res = await fetch(url, { 
      cache: 'no-store',
      headers: {
        'Accept': 'application/json',
      }
    });
    
    if (!res.ok) {
      throw new Error(`Failed to load products: ${res.status} ${res.statusText}`);
    }
    
    return res.json();
  } catch (error) {
    if (error instanceof TypeError && error.message.includes('fetch')) {
      const errorMsg = `Network error: Unable to reach API at ${url}. Please check if the backend is running and VITE_API_URL is configured.`;
      if (import.meta.env.DEV) {
        console.error('[API Error]', errorMsg, '| VITE_API_URL:', import.meta.env.VITE_API_URL || 'not set');
      }
      throw new Error(errorMsg);
    }
    throw error;
  }
}

export async function sendContact(data: { name: string; email: string; message: string }): Promise<boolean> {
  const res = await fetch(buildUrl('/api/contact'), {
    method: 'POST',
    headers: { 
      'Content-Type': 'application/json',
      'Accept': 'application/json',
    },
    body: JSON.stringify(data),
  });
  
  if (!res.ok) {
    const errorData = await res.json().catch(() => ({}));
    throw new Error(errorData.message || `Failed to send message: ${res.status} ${res.statusText}`);
  }
  
  return true;
}