import React, { ReactNode, useCallback, useEffect, useState } from 'react'
import useEmblaCarousel, { EmblaOptionsType } from 'embla-carousel-react'
import { NextButton, PrevButton } from './EmblaCarouselButtons'

type PropType = {
  options?: EmblaOptionsType
  slides: ReactNode[]
}

export const EmblaCarousel = (props: PropType) => {
  const { options, slides } = props
  const [viewportRef,embla] = useEmblaCarousel(options)

  const scrollPrev = useCallback(() => embla && embla.scrollPrev(), [embla]);
  const scrollNext = useCallback(() => embla && embla.scrollNext(), [embla]);

  const [prevBtnEnabled, setPrevBtnEnabled] = useState(false);
  const [nextBtnEnabled, setNextBtnEnabled] = useState(false);

  const onSelect = useCallback(() => {
    if (!embla) return;
    setPrevBtnEnabled(embla.canScrollPrev());
    setNextBtnEnabled(embla.canScrollNext());
  }, [embla]);

  useEffect(() => {
    if (!embla) return;
    embla.on("select", onSelect);
    onSelect();
  }, [embla, onSelect]);

  return (
    <div className="embla">
    <div className="embla__viewport" ref={viewportRef}>
      <div className="embla__container">
        {slides.map((index,deneme) => (
          <div className="embla__slide" key={deneme}>
            <div className="embla__slide__inner">
                {index}
            </div>
          </div>
        ))}
      </div>
    </div>
    <PrevButton onClick={scrollPrev} enabled={prevBtnEnabled} />
    <NextButton onClick={scrollNext} enabled={nextBtnEnabled} />
  </div>
  )
}