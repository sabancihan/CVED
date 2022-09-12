import React, { ReactNode, useCallback, useEffect, useState } from 'react'
import useEmblaCarousel, { EmblaOptionsType } from 'embla-carousel-react'
import { NextButton, PrevButton } from './EmblaCarouselButtons'


const inputClassName = `w-full rounded border border-gray-500 px-2 py-1 text-lg text-black`;

export const SoftwareAddInputForm = () => {
    return (

        <>
        
       <p>
            <label className="text-white">
              Üretici:{" "}
              <input 
              required
                type="text"
                name="vendor_name"
                className={inputClassName}
              />
            </label>
          </p>
          <p>
            <label className="text-white">
              Ad:{" "}
              <input
              required
                type="text"
                name="product_name"
                className={inputClassName}
              />
            </label>
          </p>
  
          
          <p>
            <label className="text-white">
              Versiyon:{" "}
              <input
              required
                type="text"
                name="version"
                className={inputClassName}
              />
            </label>
          </p>
  
   
          </>

      );
}