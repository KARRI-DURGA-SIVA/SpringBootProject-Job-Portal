import type { Config } from "tailwindcss";

export default {
  content: ["./index.html", "./src/**/*.{ts,tsx}"],
  theme: {
    extend: {
      colors: {
        border: "hsl(214 32% 91%)",
        background: "hsl(210 40% 98%)",
        foreground: "hsl(222 47% 11%)",
        primary: {
          DEFAULT: "hsl(217 91% 40%)",
          foreground: "hsl(0 0% 100%)"
        },
        muted: {
          DEFAULT: "hsl(214 32% 91%)",
          foreground: "hsl(215 16% 47%)"
        }
      },
      boxShadow: {
        soft: "0 10px 30px rgba(15, 23, 42, 0.08)"
      }
    }
  },
  plugins: []
} satisfies Config;
